package com.jiwell;

/**
 * Author: 98050
 * Time: 2018-08-03 20:41
 * Feature:
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author li
 * @time: 2018-08-03 20:41
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class JwApiGateway {
    public static void main(String[] args) {
        SpringApplication.run(JwApiGateway.class,args);
    }
}
