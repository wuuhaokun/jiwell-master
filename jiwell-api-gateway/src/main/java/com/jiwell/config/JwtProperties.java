package com.jiwell.config;

import  com.jiwell.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PublicKey;

/**
 * @Author: 98050
 * @Time: 2018-10-24 16:12
 * @Feature: jwt屬性
 */
//@ConfigurationProperties(prefix = "jiwell.jwt")
@Configuration
@RefreshScope
public class JwtProperties {
    /**
     * 公鑰
     */
    private PublicKey publicKey;

    /**
     * 公鑰地址
     */
    @Value("${jiwell.jwt.pubKeyPath}")
    private String pubKeyPath;

    /**
     * cookie名字
     */
    @Value("${jiwell.jwt.cookieName}")
    private String cookieName;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public static Logger getLogger() {
        return logger;
    }

    /**
     * @PostConstruct :在構造方法執行之後執行該方法
     */
    @PostConstruct
    public void init(){
        try {
            // 獲取公鑰
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            logger.error("獲取公鑰失敗！", e);
            throw new RuntimeException();
        }
    }
}
