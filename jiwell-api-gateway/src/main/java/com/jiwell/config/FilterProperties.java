package com.jiwell.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-24 16:55
 * @Feature: 過濾白名單
 */
//@ConfigurationProperties(prefix = "jiwell.filter")
@Configuration
@RefreshScope
public class FilterProperties {

    @Value("${jiwell.filter.allowPaths}")
    private String allowPaths;

    public String getAllowPaths() {
        return allowPaths;
    }

    public void setAllowPaths(String allowPaths) {
        this.allowPaths = allowPaths;
    }
}
