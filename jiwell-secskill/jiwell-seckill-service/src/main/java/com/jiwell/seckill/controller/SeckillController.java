package com.jiwell.seckill.controller;


import com.jiwell.auth.entity.UserInfo;
import com.jiwell.item.pojo.SeckillGoods;
import com.jiwell.order.pojo.SeckillOrder;
import com.jiwell.response.Result;
import com.jiwell.seckill.access.AccessLimit;
import com.jiwell.seckill.client.GoodsClient;
import com.jiwell.seckill.interceptor.LoginInterceptor;
import com.jiwell.seckill.service.SeckillService;


import com.jiwell.seckill.vo.SeckillMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @Author: 98050
 * @Time: 2018-11-10 16:57
 * @Feature:
 */
@RestController
@RequestMapping
public class SeckillController implements InitializingBean {

    @Autowired
    private  SeckillService seckillService;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "jiwell:seckill:stock";

    private Map<Long,Boolean> localOverMap = new HashMap<>();

    /**
     * 系統初始化，初始化秒殺商品數量
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //1.查询可以秒杀的商品
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        if(hashOperations != null) {
            //if (hashOperations.hasKey("12_") == false){
            hashOperations.entries().forEach((m, n) -> localOverMap.put(Long.parseLong(m.toString()), false));
//        }
        }
    }


    /**
     * 秒殺
     * @param path
     * @param seckillGoods
     * @return
     */
    @PostMapping("/{path}/seck")
    public ResponseEntity<String> seckillOrder(@PathVariable("path") String path, @RequestBody SeckillGoods seckillGoods){

        String result = "in the line!!";

        UserInfo userInfo = LoginInterceptor.getLoginUser();

        //1.驗證路徑
        boolean check = this.seckillService.checkSeckillPath(seckillGoods.getId(),userInfo.getId(),path);
        if (check){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //2.內存標記，減少redis訪問
        if(localOverMap.size() < 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        boolean over = localOverMap.get(seckillGoods.getSkuId());
        if (over){
            return ResponseEntity.ok(result);
        }

        //3.讀取庫存，減一後更新緩存
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX);
        Long stock = hashOperations.increment(seckillGoods.getSkuId().toString(), -1);

        //4.庫存不足直接返回
        if (stock < 0){
            localOverMap.put(seckillGoods.getSkuId(),true);
            return ResponseEntity.ok(result);
        }

        //5.庫存充足，請求入隊
        //5.1 獲取用戶信息
        SeckillMessage seckillMessage = new SeckillMessage(userInfo,seckillGoods);
        //5.2 發送消息
        this.seckillService.sendMessage(seckillMessage);

        return ResponseEntity.ok(result);
    }

    /**
     * 根據userId查詢訂單號
     * @param userId
     * @return
     */
    @GetMapping("orderId")
    public ResponseEntity<Long> checkSeckillOrder(Long userId){
        Long result = this.seckillService.checkSeckillOrder(userId);
        if (result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);

    }

    /**
     * 根據userId查詢訂單號
     * @param userId
     * @return
     */
    @GetMapping("orders")
    public ResponseEntity<List<SeckillOrder>> getSeckillOrders(Long userId){
        List<SeckillOrder> result = this.seckillService.getSeckillOrders(userId);
        if (result == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(result);
    }
    /**
     * 創建秒殺路徑
     * @param goodsId
     * @return
     */
    @AccessLimit(seconds = 20,maxCount = 5,needLogin = true)
    @GetMapping("get_path/{goodsId}")
    public ResponseEntity<String> getSeckillPath(@PathVariable("goodsId") Long goodsId){
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        if (userInfo == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String str = this.seckillService.createPath(goodsId,userInfo.getId());
        if (StringUtils.isEmpty(str)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(str);
    }

}
