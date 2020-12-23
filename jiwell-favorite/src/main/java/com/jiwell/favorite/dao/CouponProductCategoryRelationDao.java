package com.jiwell.favorite.dao;

import com.jiwell.favorite.pojo.CouponProductCategoryRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券和商品分类关系自定义Dao
 * Created by macro on 2018/8/28.
 */
public interface CouponProductCategoryRelationDao {
    int insertList(@Param("list")List<CouponProductCategoryRelation> productCategoryRelationList);
}
