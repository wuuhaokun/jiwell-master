package com.jiwell.fcm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
//@EnableScheduling
@EnableDiscoveryClient
@MapperScan("com.jiwell.fcm.mapper")
public class JwFcmApplication {
	public static void main(String[] args) {
		SpringApplication.run(JwFcmApplication.class, args);
	}
}
