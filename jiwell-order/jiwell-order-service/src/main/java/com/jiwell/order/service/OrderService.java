package com.jiwell.order.service;

import com.jiwell.common.pojo.PageResult;
import com.jiwell.order.pojo.Order;
import com.jiwell.order.pojo.OrderStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:34
 * @Feature:
 */
public interface OrderService {
    /**
     * 訂單創建
     * @param order
     * @return
     */
    Long createOrder(Order order);

    /**
     * 移除訂單
     * @param orderId
     * @return
     */
    Boolean deleteOrder(long orderId);

    /**
     * 根據訂單號查詢訂單
     * @param id
     * @return
     */
    Order queryOrderById(Long id);

    /**
     * 分頁查詢用戶訂單
     * @param page
     * @param rows
     * @param status
     * @return
     */
    PageResult<Order> queryUserOrderList(Integer page, Integer rows, Integer status);

    /**
     * 更改訂單狀態
     * @param id
     * @param status
     * @return
     */
    Boolean updateOrderStatus(Long id, Integer status);

    /**
     * 根據訂單號查詢商品id
     * @param id
     * @return
     */
    List<Long> querySkuIdByOrderId(Long id);

    /**
     * 根據訂單號查詢訂單狀態
     * @param id
     * @return
     */
    OrderStatus queryOrderStatusById(Long id);

    /**
     * 查詢庫存
     * @param order
     * @return
     */
    List<Long> queryStock(Order order);
}
