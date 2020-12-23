package com.jiwell.favorite.domain;


import com.jiwell.favorite.model.ProductFullReduction;
import com.jiwell.favorite.model.ProductLadder;
import com.jiwell.item.pojo.Sku;
import com.jiwell.item.pojo.Stock;

import java.util.List;

/**
 * Created by macro on 2018/8/27.
 * 商品的促销信息，包括sku、打折优惠、满减优惠
 */
public class PromotionProduct extends Sku {
    //商品库存信息
    private List<Stock> skuStockList;
    //商品打折信息
    private List<ProductLadder> productLadderList;
    //商品满减信息
    private List<ProductFullReduction> productFullReductionList;

    public List<Stock> getSkuStockList() {
        return skuStockList;
    }

    public void setSkuStockList(List<Stock> skuStockList) {
        this.skuStockList = skuStockList;
    }

    public List<ProductLadder> getProductLadderList() {
        return productLadderList;
    }

    public void setProductLadderList(List<ProductLadder> productLadderList) {
        this.productLadderList = productLadderList;
    }

    public List<ProductFullReduction> getProductFullReductionList() {
        return productFullReductionList;
    }

    public void setProductFullReductionList(List<ProductFullReduction> productFullReductionList) {
        this.productFullReductionList = productFullReductionList;
    }
}
