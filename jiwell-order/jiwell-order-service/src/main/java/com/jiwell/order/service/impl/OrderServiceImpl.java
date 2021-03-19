package com.jiwell.order.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.common.pojo.PageResult;
import com.jiwell.item.pojo.Stock;
import com.jiwell.order.client.GoodsClient;
import com.jiwell.order.interceptor.LoginInterceptor;
import com.jiwell.order.mapper.*;
import com.jiwell.order.pojo.Order;
import com.jiwell.order.pojo.OrderDetail;
import com.jiwell.order.pojo.OrderStatus;
import com.jiwell.order.pojo.SeckillOrder;
import com.jiwell.order.service.OrderService;
import com.jiwell.order.service.OrderStatusService;
import com.jiwell.order.vo.OrderStatusMessage;
import com.jiwell.utils.IdWorker;
import com.jiwell.utils.JsonUtils;
import com.jiwell.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.SoundbankResource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:37
 * @Feature:
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;

//    private static final String KEY_PREFIX = "user:code:phone";

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long createOrder(Order order) {
//創建訂單
        //1.生成orderId
        long orderId = idWorker.nextId();
        //2.獲取登錄的用戶
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        //3.初始化數據
        order.setBuyerNick(userInfo.getAccount());
        order.setBuyerRate(false);
        order.setCreateTime(new Date());
        order.setOrderId(orderId);
        order.setUserId(userInfo.getId());
        //4.保存數據
        this.orderMapper.insertSelective(order);

        //5.保存訂單狀態
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCreateTime(order.getCreateTime());
        //初始狀態未未付款：1
        orderStatus.setStatus(1);
        //6.保存數據
        this.orderStatusMapper.insertSelective(orderStatus);

        //7.在訂單詳情中添加orderId
        order.getOrderDetails().forEach(orderDetail -> {
            //添加訂單
            orderDetail.setOrderId(orderId);
        });

        //8.保存訂單詳情，使用批量插入功能
        this.orderDetailMapper.insertList(order.getOrderDetails());

        order.getOrderDetails().forEach(orderDetail -> this.stockMapper.reduceStock(orderDetail.getSkuId(), orderDetail.getNum()));

        //this.pushNotifycation(userInfo.getId().toString());
        return orderId;

    }
    /**
     * 根據訂單號查移除訂單
     * @param orderId
     * @return
     */
    @Override
    public Boolean deleteOrder(long orderId) {
        Order order = this.queryOrderById(orderId);
        if(order == null){
            //return null;
        }
        int result = this.orderMapper.deleteByPrimaryKey(orderId);
        if(result == 0){
            //return null;
        }
        //8.保存訂單詳情，使用批量插入功能
        order.getOrderDetails().forEach(orderDetail -> this.stockMapper.increaseStock(orderDetail.getSkuId(), orderDetail.getNum()));

        result = this.orderStatusMapper.deleteByPrimaryKey(orderId);
        if(result == 0){
            //return null;
        }

        this.orderDetailMapper.deleteByOrderIdInOrderDetail(orderId);
        if(result == 0){
            //return null;
        }
        return true;
    }

    /**
     * 根據訂單號查詢訂單
     * @param id
     * @return
     */
    @Override
    public Order queryOrderById(Long id) {
        //1.查詢訂單
        Order order = this.orderMapper.selectByPrimaryKey(id);
        //2.查詢訂單詳情
        Example example = new Example(OrderDetail.class);
        example.createCriteria().andEqualTo("orderId",id);
        List<OrderDetail> orderDetail = this.orderDetailMapper.selectByExample(example);
        orderDetail.forEach(System.out::println);
        //3.查詢訂單狀態
        OrderStatus orderStatus = this.orderStatusMapper.selectByPrimaryKey(order.getOrderId());
        //4.order對象填充訂單詳情
        order.setOrderDetails(orderDetail);
        //5.order對象設置訂單狀態
        order.setStatus(orderStatus.getStatus());
        //6.返回order
        return order;
    }

    /**
     * 查詢當前登錄用戶的訂單，通過訂單狀態進行篩選
     * @param page
     * @param rows
     * @param status
     * @return
     */
    @Override
    public PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status) {
        try{
            //1.分頁
            PageHelper.startPage(page,rows);
            //2.獲取登錄用戶
            UserInfo userInfo = LoginInterceptor.getLoginUser();
            //3.查詢
            Page<Order> pageInfo = (Page<Order>) this.orderMapper.queryOrderList(userInfo.getId(), status);
            //4.填充orderDetail
            List<Order> orderList = pageInfo.getResult();
            orderList.forEach(order -> {
                Example example = new Example(OrderDetail.class);
                example.createCriteria().andEqualTo("orderId",order.getOrderId());
                List<OrderDetail> orderDetailList = this.orderDetailMapper.selectByExample(example);
                order.setOrderDetails(orderDetailList);
            });
            return new PageResult<>(pageInfo.getTotal(),(long)pageInfo.getPages(), orderList);
        }catch (Exception e){
            logger.error("查詢訂單出錯",e);
            return null;
        }
    }

    /**
     * 更新訂單狀態
     * @param id
     * @param status
     * @return
     */
    @Override
    public Boolean updateOrderStatus(Long id, Integer status) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Long spuId = this.goodsClient.querySkuById(findSkuIdByOrderId(id)).getSpuId();

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(id);
        orderStatus.setStatus(status);

        //延時消息
        OrderStatusMessage orderStatusMessage = new OrderStatusMessage(id,userInfo.getId(),userInfo.getAccount(),spuId,1);
        OrderStatusMessage orderStatusMessage2 = new OrderStatusMessage(id,userInfo.getId(),userInfo.getAccount(),spuId,2);
        //1.根據狀態判斷要修改的時間
        switch (status){
            case 2:
                //2.付款時間
                orderStatus.setPaymentTime(new Date());
                break;
            case 3:
                //3.發貨時間
                orderStatus.setConsignTime(new Date());
                //發送消息到延遲隊列，防止用戶忘記確認收貨
                orderStatusService.sendMessage(orderStatusMessage);
                orderStatusService.sendMessage(orderStatusMessage2);
                break;
            case 4:
                //4.確認收貨，訂單結束
                orderStatus.setEndTime(new Date());
                orderStatusService.sendMessage(orderStatusMessage2);
                break;
            case 5:
                //5.交易失敗，訂單關閉
                orderStatus.setCloseTime(new Date());
                break;
            case 6:
                //6.評價時間
                orderStatus.setCommentTime(new Date());
                break;

            default:
                return null;
        }
        int count = this.orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        return count == 1;
    }

    /**
     * 根據訂單號查詢商品id
     * @param id
     * @return
     */
    @Override
    public List<Long> querySkuIdByOrderId(Long id) {
        Example example = new Example(OrderDetail.class);
        example.createCriteria().andEqualTo("orderId",id);
        List<OrderDetail> orderDetailList = this.orderDetailMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        orderDetailList.forEach(orderDetail -> ids.add(orderDetail.getSkuId()));
        return ids;
    }

    /**
     * 根據訂單號查詢訂單狀態
     * @param id
     * @return
     */
    @Override
    public OrderStatus queryOrderStatusById(Long id) {
        return this.orderStatusMapper.selectByPrimaryKey(id);
    }

    /**
     * 查詢訂單下商品的庫存，返回庫存不足的商品Id
     * @param order
     * @return
     */
    @Override
    public List<Long> queryStock(Order order) {
        List<Long> skuId = new ArrayList<>();
        order.getOrderDetails().forEach(orderDetail -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(orderDetail.getSkuId());
            if (stock.getStock() - orderDetail.getNum() < 0){
                //先判斷庫存是否充足
                skuId.add(orderDetail.getSkuId());
            }
        });
        return skuId;
    }

    /**
     * 根據訂單id查詢其skuId
     * @param id
     * @return
     */
    public Long findSkuIdByOrderId(Long id){
        Example example = new Example(OrderDetail.class);
        example.createCriteria().andEqualTo("orderId", id);
        List<OrderDetail> orderDetail = this.orderDetailMapper.selectByExample(example);
        return orderDetail.get(0).getSkuId();
    }

    /**
     * 發送短信驗證碼
     * @param userId
     */
    //@Override
    public Boolean pushNotifycation(String userId) {
        String message = "訂單己成立";
        try {
            Map<String,String> msg = new HashMap<>();
            msg.put("userId",userId);
            msg.put("message",message);
            //2.發送短信
            this.amqpTemplate.convertAndSend("jiwell.fcm.exchange","fcm.verify.code",msg);

            //3.將code存入redis
            //this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MINUTES);

            return true;

        }catch (Exception e){
            logger.error("發送短信失敗。phone：{}，code：{}",userId,message);
            return false;
        }
    }
}
