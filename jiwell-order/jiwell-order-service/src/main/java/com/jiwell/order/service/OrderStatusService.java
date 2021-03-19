package com.jiwell.order.service;

import com.jiwell.order.vo.CommentsParameter;
import com.jiwell.order.vo.OrderStatusMessage;

/**
 * @Author: 98050
 * @Time: 2018-12-10 23:17
 * @Feature: 發送延時消息和評論消息
 */
public interface OrderStatusService {


    /**
     * 發送消息到延時隊列
     * @param orderStatusMessage
     */
    void sendMessage(OrderStatusMessage orderStatusMessage);

    /**
     * 發送評論信息
     * @param commentsParameter
     */
    void sendComments(CommentsParameter commentsParameter);
}
