package com.jiwell.seckill.service;


import com.jiwell.item.pojo.SeckillGoods;
import com.jiwell.order.pojo.SeckillOrder;
import com.jiwell.seckill.vo.SeckillMessage;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-10 16:56
 * @Feature:
 */
public interface SeckillService {

    /**
     * 創建訂單
     * @param seckillGoods
     * @return
     */
    Long createOrder(SeckillGoods seckillGoods);


    /**
     * 檢查庫存
     * @param skuId
     * @return
     */
    boolean queryStock(Long skuId);

    /**
     * 發送秒殺信息到隊列當中
     * @param seckillMessage
     */
    void sendMessage(SeckillMessage seckillMessage);

    /**
     * 根據用戶id查詢秒殺訂單
     * @param userId
     * @return
     */
    Long checkSeckillOrder(Long userId);


    /**
     * 根據用戶id查詢秒殺訂單
     * @param userId
     * @return
     */
    List<SeckillOrder> getSeckillOrders(Long userId);


    /**
     * 創建秒殺地址
     * @param goodsId
     * @param id
     * @return
     */
    String createPath(Long goodsId, Long id);

    /**
     * 驗證秒殺地址
     * @param goodsId
     * @param id
     * @param path
     * @return
     */
    boolean checkSeckillPath(Long goodsId, Long id, String path);

}
