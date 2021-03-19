package com.jiwell.config.myconfig;

import come.jiwell.config.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: 98050
 * @Time: 2018-11-30 20:56
 * @Feature:
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean someFilterRegistration1() {
        //新建過濾器註冊類
        FilterRegistrationBean registration = new FilterRegistrationBean();
        // 添加我們寫好的過濾器
        registration.setFilter( new MyFilter());
        // 設置過濾器的URL模式
        registration.addUrlPatterns("/*");
        return registration;
    }
}
