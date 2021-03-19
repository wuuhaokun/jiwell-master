package com.jiwell.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author li
 */
@Table(name = "tb_stock")
public class Stock {

    @Id
    private Long skuId;
    /**
     * 秒殺可用庫存
     */
    private Integer seckillStock;
    /**
     * 已秒殺數量
     */
    private Integer seckillTotal;
    /**
     * 正常庫存
     */
    private Long stock;

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Integer getSeckillStock() {
        return seckillStock;
    }

    public void setSeckillStock(Integer seckillStock) {
        this.seckillStock = seckillStock;
    }

    public Integer getSeckillTotal() {
        return seckillTotal;
    }

    public void setSeckillTotal(Integer seckillTotal) {
        this.seckillTotal = seckillTotal;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "skuId=" + skuId +
                ", seckillStock=" + seckillStock +
                ", seckillTotal=" + seckillTotal +
                ", stock=" + stock +
                '}';
    }
}
