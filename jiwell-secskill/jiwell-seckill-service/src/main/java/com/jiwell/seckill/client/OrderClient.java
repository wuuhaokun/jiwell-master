package com.jiwell.seckill.client;

import com.jiwell.order.api.OrderApi;
import com.jiwell.seckill.config.OrderConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 98050
 * @Time: 2018-11-12 15:19
 * @Feature: 订单接口
 */
//需於提無端提供config資料，不然無法傳HttpServletRequest資料傳遞使用 參考 OrderConfig
@FeignClient(value = "order-service",configuration = OrderConfig.class)
public interface OrderClient extends OrderApi {
}
