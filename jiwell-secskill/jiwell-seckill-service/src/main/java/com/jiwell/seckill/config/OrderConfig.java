package com.jiwell.seckill.config;


import feign.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author: 98050
 * @Time: 2018-11-13 22:39
 * @Feature: order服务配置，转发header
 */
@Configuration
//這個class 是為feign的HttpServletRequest資料傳遞使用，沒加會無法取得HttpServletRequest相關資料，使用端範例@FeignClient(value = "order-service",configuration = OrderConfig.class)
public class OrderConfig {
    @Bean
    public Feign.Builder feignBuilder(){
        return Feign.builder().requestInterceptor(requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    requestTemplate.header(name, values);
                }
            }

            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
                requestTemplate.header("Authorization", bearerToken);
            }

        });
    }
}
