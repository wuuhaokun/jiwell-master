package com.jiwell.favorite.dao;

import com.jiwell.favorite.pojo.CouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 優惠券和產品關係自定義Dao
 * Created by macro on 2018/8/28.
 */
public interface CouponProductRelationDao {
    int insertList(@Param("list")List<CouponProductRelation> productRelationList);
}
