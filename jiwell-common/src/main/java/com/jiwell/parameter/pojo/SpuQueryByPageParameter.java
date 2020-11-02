package com.jiwell.parameter.pojo;

/**
 * @Author: 98050
 * Time: 2018-08-14 22:19
 * Feature:
 */
public class SpuQueryByPageParameter extends BrandQueryByPageParameter{
    /**
     *         - page：当前页，int
     *         - rows：每页大小，int
     *         - sortBy：排序字段，String
     *         - desc：是否为降序，boolean
     *         - key：搜索关键词，String
     *         - saleable: 是否上下架
     */
    private Boolean saleable;

    private Long cid3;

    private Long brandId;

    public Boolean getSaleable() {
        return saleable;
    }

    public void setSaleable(Boolean saleable) {
        this.saleable = saleable;
    }

    public long getCid3() {
        return cid3;
    }

    public void setCid3(long cid3) {
        this.cid3 = cid3;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }

    public SpuQueryByPageParameter(Integer page, Integer rows, String sortBy, Boolean desc, String key, Boolean saleable, long cid3,long brandId) {
        super(page, rows, sortBy, desc, key);
        this.saleable = saleable;
        this.cid3 = cid3;
        this.brandId = brandId;
    }

    public SpuQueryByPageParameter(Boolean saleable) {
        this.saleable = saleable;
    }

    @Override
    public String toString() {
        return "SpuQueryByPageParameter{" +
                "saleable=" + saleable +
                '}';
    }
}
