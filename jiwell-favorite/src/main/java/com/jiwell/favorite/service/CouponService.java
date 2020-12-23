package com.jiwell.favorite.service;

//import com.macro.mall.common.api.CommonResult;
//import com.macro.mall.model.SmsCouponHistory;
//import com.macro.mall.portal.domain.CartPromotionItem;
//import com.macro.mall.portal.domain.SmsCouponHistoryDetail;

import com.jiwell.favorite.domain.CartPromotionItem;
import com.jiwell.favorite.domain.CouponHistoryDetail;
import com.jiwell.favorite.dto.CouponParam;
import com.jiwell.favorite.pojo.CouponHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户优惠券管理Service
 * Created by macro on 2018/8/29.
 */
public interface CouponService {
    /**
     * 会员添加优惠券
     */
    @Transactional
    Boolean add(Long couponId);

    /**
     * 获取优惠券列表
     * @param useStatus 优惠券的使用状态
     */
    List<CouponHistory> list(Integer useStatus);

    /**
     * 根据购物车信息获取可用优惠券
     */
    List<CouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type);

    //以下為管理介界使用

    /**
     * 添加优惠券
     */
    @Transactional
    int create(CouponParam couponParam);

    /**
     * 根据优惠券id删除优惠券
     */
    @Transactional
    int delete(Long id);

    /**
     * 根据优惠券id更新优惠券信息
     */
    @Transactional
    int update(Long id, CouponParam couponParam);
//
//    /**
//     * 分页获取优惠券列表
//     */
//    List<Coupon> list(String name, Integer type, Integer pageSize, Integer pageNum);
//
    /**
     * 获取优惠券详情
     * @param id 优惠券表id
     */
    CouponParam getItem(Long id);
}
