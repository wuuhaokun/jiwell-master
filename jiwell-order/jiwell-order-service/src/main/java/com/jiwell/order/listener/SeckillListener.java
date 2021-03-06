package com.jiwell.order.listener;

import com.jiwell.auth.entity.UserInfo;
import com.jiwell.item.pojo.SeckillGoods;
import com.jiwell.item.pojo.Stock;
import com.jiwell.order.interceptor.LoginInterceptor;
import com.jiwell.order.mapper.*;
import com.jiwell.order.pojo.*;
import com.jiwell.order.service.OrderService;
import com.jiwell.seckill.vo.SeckillMessage;
import com.jiwell.utils.IdWorker;
import com.jiwell.utils.JsonUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-15 20:30
 * @Feature: 秒殺消息隊列監聽器
 */
@Component
public class SeckillListener {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 接收秒殺信息
     * @param seck
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "jiwell.order.seckill.queue",durable = "true"), //队列持久化
            exchange = @Exchange(
                    value = "jiwell.order.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {"order.seckill"}
    ))
    @Transactional(rollbackFor = Exception.class)
    public void listenSeckill(String seck){
        SeckillMessage seckillMessage = JsonUtils.parse(seck,SeckillMessage.class);
        UserInfo userInfo = seckillMessage.getUserInfo();
        SeckillGoods seckillGoods = seckillMessage.getSeckillGoods();


        //1.首先判斷庫存是否充足
        Stock stock = stockMapper.selectByPrimaryKey(seckillGoods.getSkuId());
        if (stock.getSeckillStock() <= 0 || stock.getStock() <= 0){
            //如果庫存不足的話修改秒殺商品的enable字段
            Example example = new Example(SeckillGoods.class);
            example.createCriteria().andEqualTo("skuId", seckillGoods.getSkuId());
            List<SeckillGoods> list = this.seckillMapper.selectByExample(example);
            for (SeckillGoods temp : list){
                if (temp.getEnable()){
                    temp.setEnable(false);
                    this.seckillMapper.updateByPrimaryKeySelective(temp);
                }
            }
            return;
        }
        //2.判斷此用戶是否已經秒殺到了
        Example example = new Example(SeckillOrder.class);
        example.createCriteria().andEqualTo("userId",userInfo.getId()).andEqualTo("skuId",seckillGoods.getSkuId());
        List<SeckillOrder> list = this.seckillOrderMapper.selectByExample(example);
        if (list.size() > 0){
            return;
        }

        Example addressExample = new Example(Address.class);
        addressExample.createCriteria().andEqualTo("userId",userInfo.getId()).andEqualTo("defaultAddress",true);
        List<Address> addressList = this.addressMapper.selectByExample(addressExample);
        Address address = addressList.get(0);
        //3.下訂單
        //構造order對象
        Order order = new Order();
        order.setPaymentType(1);
        order.setTotalPay(seckillGoods.getSeckillPrice());
        order.setActualPay(seckillGoods.getSeckillPrice());
        if (addressList.size() > 0){
            //寫入地址資料
            order.setPostFee(0+"");
            order.setReceiver(address.getName());
            order.setReceiverMobile(address.getPhone());
            order.setReceiverCity(address.getCity());
            order.setReceiverDistrict(address.getDistrict());
            order.setReceiverState(address.getState());
            order.setReceiverZip(address.getZipCode());
            order.setReceiverAddress(address.getAddress());
        }

        order.setInvoiceType(0);
        order.setSourceType(2);

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSkuId(seckillGoods.getSkuId());
        orderDetail.setNum(1);
        orderDetail.setTitle(seckillGoods.getTitle());
        orderDetail.setImage(seckillGoods.getImage());
        orderDetail.setPrice(seckillGoods.getSeckillPrice());
        orderDetail.setOwnSpec(this.skuMapper.selectByPrimaryKey(seckillGoods.getSkuId()).getOwnSpec());

        order.setOrderDetails(Arrays.asList(orderDetail));

        //3.1 生成orderId
        long orderId = idWorker.nextId();
        //3.2 初始化數據
        order.setBuyerNick(userInfo.getAccount());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(userInfo.getId());
        //3.3 保存數據
        this.orderMapper.insertSelective(order);

        //3.4 保存訂單狀態
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        //初始狀態未未付款：1
        orderStatus.setStatus(1);
        //3.5 保存數據
        this.orderStatusMapper.insertSelective(orderStatus);

        //3.6 在訂單詳情中添加orderId
        order.getOrderDetails().forEach(od -> {
            //添加訂單
            od.setOrderId(orderId);
        });

        //3.7 保存訂單詳情，使用批量插入功能
        this.orderDetailMapper.insertList(order.getOrderDetails());

        //3.8 修改庫存
        order.getOrderDetails().forEach(ord -> {
            Stock stock1 = this.stockMapper.selectByPrimaryKey(ord.getSkuId());
            stock1.setStock(stock1.getStock() - ord.getNum());
            stock1.setSeckillStock(stock1.getSeckillStock() - ord.getNum());
            this.stockMapper.updateByPrimaryKeySelective(stock1);

            //新建秒殺訂單
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setOrderId(orderId);
            seckillOrder.setSkuId(ord.getSkuId());
            seckillOrder.setUserId(userInfo.getId());
            this.seckillOrderMapper.insert(seckillOrder);

        });

    }
}




