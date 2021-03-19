package com.jiwell.item.pojo;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author li
 */
@Table(name="tb_spu_detail")
public class SpuDetail {
    @Id
/**
 * 對應的SPU的id
 */
    private Long spuId;
    /**
     * 商品描述
     */
    private String description;
    /**
     * 商品特殊規格的名稱及可選值模板
     */
    private String specTemplate;
    /**
     * 商品的全局規格屬性
     */
    private String specifications;
    /**
     * 包裝清單
     */
    private String packingList;
    /**
     * 售後服務
     */
    private String afterService;

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSpecTemplate() {
        return specTemplate;
    }

    public void setSpecTemplate(String specTemplate) {
        this.specTemplate = specTemplate;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getAfterService() {
        return afterService;
    }

    public void setAfterService(String afterService) {
        this.afterService = afterService;
    }
}
