package com.jiwell.favorite.dao;

import com.jiwell.favorite.pojo.CouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 優惠券和商品分類關係自定義Dao
 * Created by macro on 2018/8/28.
 */
public interface CouponProductCategoryRelationDao {
    int insertList(@Param("list")List<CouponProductCategoryRelation> productCategoryRelationList);
}
