package com.jiwell.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 98050
 * @Time: 2018-10-24 20:46
 * @Feature:購物車啟動器
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class JwCartApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwCartApplication.class,args);
    }
}
