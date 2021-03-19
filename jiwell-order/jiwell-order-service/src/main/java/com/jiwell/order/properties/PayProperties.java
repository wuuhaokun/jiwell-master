package com.jiwell.order.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 98050
 * @create: 2018-10-27 11:38
 **/
//@ConfigurationProperties(prefix = "jiwell.pay")
@Configuration
@RefreshScope
public class PayProperties {

    /**
     * 公眾賬號ID
     */
    @Value("${jiwell.pay.appId}")
    private String appId;

    /**
     * 商戶號
     */
    @Value("${jiwell.pay.mchId}")
    private String mchId;

    /**
     * 生成簽名的密鑰
     */
    @Value("${jiwell.pay.key}")
    private String key;

    /**
     * 連接超時時間
     */
    @Value("${jiwell.pay.connectTimeoutMs}")
    private int connectTimeoutMs;

    /**
     * 讀取超時時間
     */
    @Value("${jiwell.pay.connectTimeoutMs}")
    private int readTimeoutMs;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return readTimeoutMs;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }
}
