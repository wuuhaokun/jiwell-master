package com.jiwell.favorite.dao;

import com.jiwell.favorite.domain.CouponHistoryDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员优惠券领取历史自定义Dao
 * Created by macro on 2018/8/29.
 */
public interface CouponHistoryDao {
    List<CouponHistoryDetail> getDetailList(@Param("memberId") Long memberId);
}
