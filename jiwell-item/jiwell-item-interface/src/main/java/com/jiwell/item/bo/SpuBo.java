package com.jiwell.item.bo;

import com.jiwell.item.pojo.Spu;
import com.jiwell.item.pojo.SpuDetail;
import com.jiwell.item.pojo.Sku;

import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author: 98050
 * Time: 2018-08-14 22:10
 * Feature:
 */
public class SpuBo extends Spu {
    /**
     * 商品分類名稱
     */
    @Transient
    private String cname;
    /**
     * 品牌名稱
     */
    @Transient
    private String bname;

    /**
     * 商品詳情
     */
    @Transient
    private SpuDetail spuDetail;

    /**
     * sku列表
     */
    @Transient
    private List<Sku> skus;

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuBo() {
    }

    public SpuBo(Long brandId, Long cid1, Long cid2, Long cid3, String title, String subTitle, Boolean saleable, Boolean valid, Date createTime, Date lastUpdateTime, Long internalCategoryId, String image) {
        super(brandId, cid1, cid2, cid3, title, subTitle, saleable, valid, createTime, lastUpdateTime, internalCategoryId, image);
    }
}
