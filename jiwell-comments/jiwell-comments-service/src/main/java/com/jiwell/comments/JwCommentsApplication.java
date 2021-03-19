package com.jiwell.comments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: 98050
 * @Time: 2018-11-29 15:41
 * @Feature: 開啟feign
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class JwCommentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwCommentsApplication.class,args);
    }
}
