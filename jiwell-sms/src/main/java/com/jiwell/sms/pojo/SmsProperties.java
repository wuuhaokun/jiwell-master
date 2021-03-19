package com.jiwell.sms.pojo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 98050
 * @Time: 2018-10-22 18:34
 * @Feature: 短信服務實體類
 */
//@ConfigurationProperties(prefix = "jiwell.sms")
@Configuration
@RefreshScope
public class SmsProperties {

    @Value("${jiwell.sms.accessKeyId}")
    private String accessKeyId;

    @Value("${jiwell.sms.accessKeySecret}")
    private String accessKeySecret;

    @Value("${jiwell.sms.signName}")
    private String signName;

    @Value("${jiwell.sms.verifyCodeTemplate}")
    private String verifyCodeTemplate;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getVerifyCodeTemplate() {
        return verifyCodeTemplate;
    }

    public void setVerifyCodeTemplate(String verifyCodeTemplate) {
        this.verifyCodeTemplate = verifyCodeTemplate;
    }
}
