package com.jiwell.client;

import com.jiwell.item.api.SpecApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: 98050
 * Time: 2018-10-17 19:01
 * Feature:spec FeignClient
 */
@FeignClient(value = "item-service")
public interface SpecClient extends SpecApi {
}
