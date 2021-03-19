package com.jiwell.favorite.domain;

import com.jiwell.favorite.pojo.Coupon;
import com.jiwell.favorite.pojo.CouponHistory;
import com.jiwell.favorite.pojo.CouponProductCategoryRelation;
import com.jiwell.favorite.pojo.CouponProductRelation;

import java.util.List;

/**
 * 優惠券領取歷史詳情封裝
 * Created by macro on 2018/8/29.
 */
public class CouponHistoryDetail extends CouponHistory {
    //相關優惠券信息
    private Coupon coupon;
    //優惠券關聯商品
    private List<CouponProductRelation> productRelationList;
    //優惠券關聯商品分類
    private List<CouponProductCategoryRelation> categoryRelationList;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public List<CouponProductRelation> getProductRelationList() {
        return productRelationList;
    }

    public void setProductRelationList(List<CouponProductRelation> productRelationList) {
        this.productRelationList = productRelationList;
    }

    public List<CouponProductCategoryRelation> getCategoryRelationList() {
        return categoryRelationList;
    }

    public void setCategoryRelationList(List<CouponProductCategoryRelation> categoryRelationList) {
        this.categoryRelationList = categoryRelationList;
    }
}
