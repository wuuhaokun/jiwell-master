package com.jiwell.order.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @author: 98050
 * @create: 2018-10-27
 **/
//@ConfigurationProperties(prefix = "jiwell.worker")
@Configuration
@RefreshScope
public class IdWorkerProperties {

    /**
     * 當前機器id
     */
    @Value("${jiwell.worker.workerId}")
    private long workerId;

    /**
     * 序列號
     */
    @Value("${jiwell.worker.dataCenterId}")
    private long dataCenterId;

    public long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }

    public long getDataCenterId() {
        return dataCenterId;
    }

    public void setDataCenterId(long dataCenterId) {
        this.dataCenterId = dataCenterId;
    }
}
