package com.jiwell.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Author: 98050
 * @Time: 2018-10-21 17:29
 * @Feature: 用戶中心啟動器
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.jiwell.user.mapper")
public class JwUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(JwUserApplication.class,args);
    }
}
