package com.jiwell.favorite.dao;

import com.jiwell.favorite.domain.CouponHistoryDetail;
import com.jiwell.favorite.pojo.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 會員優惠券領取歷史自定義Dao
 * Created by macro on 2018/8/29.
 */
public interface CouponHistoryDao {
    List<CouponHistoryDetail> getDetailList(@Param("memberId") Long memberId);

    /**
     * 獲取指定會員優惠券列表
     */
    List<Coupon> getCouponList(@Param("memberId") Long memberId, @Param("useStatus")Integer useStatus);

}
