package com.jiwell.comments.client;

import com.jiwell.comments.config.OrderConfig;
import com.jiwell.order.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 98050
 * @Time: 2018-11-12 15:19
 * @Feature: 订单接口
 */
@FeignClient(value = "order-service",configuration = OrderConfig.class)
public interface OrderClient extends OrderApi {
}
