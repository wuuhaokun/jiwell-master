package com.jiwell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author li
 * @time:2018/8/9
 * 處理跨域請求的過濾器
 */
@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1) 允許的域,不要寫*，否則cookie就無法使用了
        config.addAllowedOrigin("http://manage.ji-well.com");
        config.addAllowedOrigin("http://dev-manage.ji-well.com");
        //2) 是否發送Cookie信息
        config.setAllowCredentials(false);
        //3) 允許的請求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("POST");
        config.addAllowedHeader("*");

        //2.添加映射路徑，我們攔截一切請求
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);

        //3.返回新的CorsFilter.
        return new CorsFilter(configSource);
    }
}
