package com.jiwell.favorite.dao;

//import com.macro.mall.dto.SmsCouponParam;
import com.jiwell.favorite.dto.CouponParam;
import org.apache.ibatis.annotations.Param;

/**
 * 優惠券管理自定義查詢Dao
 * Created by macro on 2018/8/29.
 */
public interface CouponDao {
    CouponParam getItem(@Param("id") Long id);
}
