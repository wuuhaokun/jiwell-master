package com.jiwell.utils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * <p>名稱：IdWorker.java</p>
 * <p>描述：分佈式自增長ID</p>
 * <pre>
 * Twitter的 Snowflake　JAVA實現方案
 * </pre>
 * 核心代碼為其IdWorker這個類實現，其原理結構如下，我分別用一個0表示一位，用—分割開部分的作用：
 * 1||0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 在上面的字符串中，第一位為未使用（實際上也可作為long的符號位），接下來的41位為毫秒級時間，
 * 然後5位datacenter標識位，5位機器ID（並不算標識符，實際是為線程標識），
 * 然後12位該毫秒內的當前毫秒內的計數，加起來剛好64位，為一個Long型。
 * 這樣的好處是，整體上按照時間自增排序，並且整個分佈式系統內不會產生ID碰撞（由datacenter和機器ID作區分），
 * 並且效率較高，經測試，snowflake每秒能夠產生26萬ID左右，完全滿足需要。
 * <p>
 * 64位ID (42(毫秒)+5(機器ID)+5(業務編碼)+12(重複累加))
 *
 * @author Polim
 */
public class IdWorker {
    /**
     * 時間起始標記點，作為基準，一般取系統的最近時間（一旦確定不能變動）
     */
    private final static long twepoch = 1288834974657L;
    /**
     * 機器標識位數
     */
    private final static long workerIdBits = 5L;
    /**
     * 數據中心標識位數
     */
    private final static long datacenterIdBits = 5L;
    /**
     * 機器ID最大值
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 數據中心ID最大值
     */
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 毫秒內自增位
     */
    private final static long sequenceBits = 12L;
    /**
     * 機器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;
    /**
     * 數據中心ID左移17位
     */
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 時間毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 上次生產id時間戳
     */
    private static long lastTimestamp = -1L;
    /**
     * 並發控制
     */
    private long sequence = 0L;

    private final long workerId;
    /**
     * 數據標識id部分
     */
    private final long datacenterId;

    public IdWorker(){
        this.datacenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(datacenterId, maxWorkerId);
    }
    /**
     * @param workerId
     * 工作機器ID
     * @param datacenterId
     * 序列號
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }
    /**
     * 獲取下一個ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 當前毫秒內，則+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 當前毫秒內計數滿了，則等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移組合生成最終的ID，並返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * <p>
     * 獲取 maxWorkerId
     * </p>
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
         /*
          * GET jvmPid
          */
            mpid.append(name.split("@")[0]);
        }
      /*
       * MAC + PID 的 hashcode 获取16个低位
       */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 數據標識id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }


}
