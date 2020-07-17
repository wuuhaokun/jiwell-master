package com.jiwell;

/**
 * Author: 98050
 * Time: 2018-08-03 20:29
 * Feature:
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author li
 * @time 2018-08-03 20:29
 */
@SpringBootApplication
@EnableEurekaServer
public class JwRegistry {
    public static void main(String[] args){
        SpringApplication.run(JwRegistry.class,args);
    }
}
