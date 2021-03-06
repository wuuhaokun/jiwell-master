package com.jiwell.order.service.impl;

import com.jiwell.order.service.OrderStatusService;
import com.jiwell.order.vo.CommentsParameter;
import com.jiwell.order.vo.OrderStatusMessage;
import com.jiwell.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: 98050
 * @Time: 2018-12-10 23:24
 * @Feature:
 */
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderStatusServiceImpl.class);


    /**
     * 發送延時消息到延時隊列中
     * @param orderStatusMessage
     */
    @Override
    public void sendMessage(OrderStatusMessage orderStatusMessage) {
        String json = JsonUtils.serialize(orderStatusMessage);
        MessageProperties properties;
        if (orderStatusMessage.getType() == 1){
            // 持久性 non-persistent (1) or persistent (2)
            properties = MessagePropertiesBuilder.newInstance().setExpiration("60000").setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        }else {
            properties = MessagePropertiesBuilder.newInstance().setExpiration("90000").setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        }

        Message message = MessageBuilder.withBody(json.getBytes()).andProperties(properties).build();
        //发送消息
        try {
            this.amqpTemplate.convertAndSend("", "jiwell.order.delay.queue", message);
        }catch (Exception e){
            LOGGER.error("延時消息發送異常，訂單號為：id：{}，用户id为：{}",orderStatusMessage.getOrderId(),orderStatusMessage.getUserId(),e);
        }
    }

    /**
     * 將評論發送到消息隊列中
     * @param commentsParameter
     */
    @Override
    public void sendComments(CommentsParameter commentsParameter) {
        String json = JsonUtils.serialize(commentsParameter);
        try {
            this.amqpTemplate.convertAndSend("jiwell.comments.exchange","user.comments", json);
        }catch (Exception e){
            LOGGER.error("評論消息發送異常，訂單id：{}",commentsParameter.getOrderId(),e);
        }
    }
}
