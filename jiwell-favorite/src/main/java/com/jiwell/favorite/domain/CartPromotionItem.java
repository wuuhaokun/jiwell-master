package com.jiwell.favorite.domain;

//import com.macro.mall.model.OmsCartItem;

import java.math.BigDecimal;

/**
 * Created by macro on 2018/8/27.
 * 購物車中促銷信息的封裝
 */
public class CartPromotionItem extends Cart{
    //促銷活動信息
    private String promotionMessage;
    //促銷活動減去的金額，針對每個商品
    private BigDecimal reduceAmount;
    //商品的真實庫存（剩餘庫存-鎖定庫存）
    private Integer realStock;
    //購買商品贈送積分
    private Integer integration;
    //購買商品贈送成長值
    private Integer growth;
    public String getPromotionMessage() {
        return promotionMessage;
    }

    public void setPromotionMessage(String promotionMessage) {
        this.promotionMessage = promotionMessage;
    }

    public BigDecimal getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(BigDecimal reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public Integer getRealStock() {
        return realStock;
    }

    public void setRealStock(Integer realStock) {
        this.realStock = realStock;
    }

    public Integer getIntegration() {
        return integration;
    }

    public void setIntegration(Integer integration) {
        this.integration = integration;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }
}
