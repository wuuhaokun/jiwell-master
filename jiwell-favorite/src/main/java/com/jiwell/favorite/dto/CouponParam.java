package com.jiwell.favorite.dto;
import java.util.List;

import com.jiwell.favorite.pojo.Coupon;
import com.jiwell.favorite.pojo.CouponProductCategoryRelation;
import com.jiwell.favorite.pojo.CouponProductRelation;

/**
 * 優惠券信息封裝，包括綁定商品和綁定分類
 * Created by macro on 2018/8/28.
 */
public class CouponParam extends Coupon {
    //优惠券绑定的商品
    private List<CouponProductRelation> productRelationList;
    //优惠券绑定的商品分类
    private List<CouponProductCategoryRelation> productCategoryRelationList;

    public List<CouponProductRelation> getProductRelationList() {
        return productRelationList;
    }

    public void setProductRelationList(List<CouponProductRelation> productRelationList) {
        this.productRelationList = productRelationList;
    }

    public List<CouponProductCategoryRelation> getProductCategoryRelationList() {
        return productCategoryRelationList;
    }

    public void setProductCategoryRelationList(List<CouponProductCategoryRelation> productCategoryRelationList) {
        this.productCategoryRelationList = productCategoryRelationList;
    }
}
