package com.jiwell.seckill.client;

import com.jiwell.order.api.OrderApi;
import com.jiwell.seckill.config.OrderConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 98050
 * @Time: 2018-11-12 15:19
 * @Feature: 订单接口
 */
@FeignClient(value = "order-service",configuration = OrderConfig.class)
public interface OrderClient extends OrderApi {
}
