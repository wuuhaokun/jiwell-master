package com.jiwell.order.utils;

import com.github.wxpay.sdk.WXPay;
import com.jiwell.order.config.PayConfig;
import com.jiwell.order.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: 98050
 * @create: 2018-10-27 15:54
 **/
@Component
public class PayHelper {

    private WXPay wxPay;

    private static final Logger logger = LoggerFactory.getLogger(PayHelper.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private OrderService orderService;

    public PayHelper(PayConfig payConfig) {
        // 真實開發時
        wxPay = new WXPay(payConfig);
        // 測試時
        // wxPay = new WXPay(payConfig, WXPayConstants.SignType.MD5, true);
    }

    public String createPayUrl(Long orderId) {
        String key = "jiwell.pay.url." + orderId;
        try {
            String url = this.redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(url)) {
                return url;
            }
        } catch (Exception e) {
            logger.error("查詢緩存付款鏈接異常,訂單編號：{}", orderId, e);
        }

        try {
            Map<String, String> data = new HashMap<>();
            // 商品描述
            data.put("body", "樂優商城測試");
            // 訂單號
            data.put("out_trade_no", orderId.toString());
            //貨幣
            data.put("fee_type", "CNY");
            //金額，單位是分
            data.put("total_fee", "1");
            //調用微信支付的終端IP（estore商城的IP）
            data.put("spbill_create_ip", "10.140.0.3");
            //回調地址
            data.put("notify_url", "http://test.jiwell.com/wxpay/notify");
            // 交易類型為掃碼支付
            data.put("trade_type", "NATIVE");
            //商品id,使用假數據
            data.put("product_id", "1234567");

            Map<String, String> result = this.wxPay.unifiedOrder(data);

            if ("SUCCESS".equals(result.get("return_code"))) {
                String url = result.get("code_url");
                // 將付款地址緩存，時間為10分鐘
                try {
                    this.redisTemplate.opsForValue().set(key, url, 10, TimeUnit.MINUTES);
                } catch (Exception e) {
                    logger.error("緩存付款鏈接異常,訂單編號：{}", orderId, e);
                }
                return url;
            } else {
                logger.error("創建預交易訂單失敗，錯誤信息：{}", result.get("return_msg"));
                return null;
            }
        } catch (Exception e) {
            logger.error("創建預交易訂單異常", e);
            return null;
        }
    }

    /**
     * 查詢訂單狀態
     *
     * @param orderId
     * @return
     */
    public PayState queryOrder(Long orderId) {
        Map<String, String> data = new HashMap<>();
        // 訂單號
        data.put("out_trade_no", orderId.toString());
        try {
            Map<String, String> result = this.wxPay.orderQuery(data);
            if (result == null) {
                // 未查詢到結果，認為是未付款
                return PayState.NOT_PAY;
            }
            String state = result.get("trade_state");
            if ("SUCCESS".equals(state)) {
                // success，則認為付款成功

                // 修改訂單狀態
                this.orderService.updateOrderStatus(orderId, 2);
                return PayState.SUCCESS;
            } else if (StringUtils.equals("USERPAYING", state) || StringUtils.equals("NOTPAY", state)) {
                // 未付款或正在付款，都認為是未付款
                return PayState.NOT_PAY;
            } else {
                // 其它狀態認為是付款失敗
                return PayState.FAIL;
            }
        } catch (Exception e) {
            logger.error("查詢訂單狀態異常", e);
            return PayState.NOT_PAY;
        }
    }
}
