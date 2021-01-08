package com.jiwell.favorite.controller;

import com.jiwell.favorite.domain.CartPromotionItem;
import com.jiwell.favorite.domain.CouponHistoryDetail;
import com.jiwell.favorite.dto.CouponParam;
import com.jiwell.favorite.pojo.Coupon;
import com.jiwell.favorite.pojo.CouponHistory;
import com.jiwell.favorite.service.CartItemService;
import com.jiwell.favorite.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户优惠券管理Controller
 * Created by macro on 2018/8/29.
 */
@RestController
//@Api(tags = "UmsMemberCouponController", description = "用户优惠券管理")
@RequestMapping("coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @Autowired
    private CartItemService cartItemService;

    @PostMapping("{couponId}")
    public ResponseEntity<Boolean> add(@PathVariable Long couponId){
        Boolean result = this.couponService.add(couponId);
        if (result == null || result == false) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(true);
    }
    ///////////////////////////////////////////
    //@ApiOperation("获取用户优惠券历史列表")
    //@ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期", allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/listHistory", method = RequestMethod.GET)
    @ResponseBody
    //public CommonResult<List<SmsCouponHistory>> listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
    public ResponseEntity<List<CouponHistory>>listHistory(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<CouponHistory> couponHistoryList = couponService.listHistory(useStatus);
        //return CommonResult.success(couponHistoryList);
        return ResponseEntity.ok(couponHistoryList);
    }

    //@ApiOperation("获取用户优惠券列表")
    //@ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",allowableValues = "0,1,2", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    //public CommonResult<List<Coupon>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
    public ResponseEntity<List<Coupon>>list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
        List<Coupon> couponList = couponService.list(useStatus);
        return ResponseEntity.ok(couponList);
        //return CommonResult.success(couponList);
    }
    ////////////////////////////////////////////
    //@ApiOperation("获取用户优惠券列表")
    //@ApiImplicitParam(name = "useStatus", value = "优惠券筛选类型:0->未使用；1->已使用；2->已过期",allowableValues = "0,1,2", paramType = "query", dataType = "integer")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public ResponseEntity<List<CouponHistory>> list(@RequestParam(value = "useStatus", required = false) Integer useStatus) {
//        List<CouponHistory> couponHistoryList = couponService.list(useStatus);
//        if (couponHistoryList == null || couponHistoryList.size() <= 0) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(couponHistoryList);
//    }

    //@ApiOperation("获取登录会员购物车的相关优惠券")
    //@ApiImplicitParam(name = "type", value = "使用可用:0->不可用；1->可用",defaultValue = "1", allowableValues = "0,1", paramType = "query", dataType = "integer")
    @RequestMapping(value = "/list/cart/{type}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<CouponHistoryDetail>> listCart(@PathVariable Integer type) {
        long userId = 1;
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(userId);
        //List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        List<CouponHistoryDetail> couponHistoryList = couponService.listCart(cartPromotionItemList,type);
        //return CommonResult.success(couponHistoryList);
        return ResponseEntity.ok(couponHistoryList);
    }

    //@ApiOperation("获取当前商品相关优惠券")
    @RequestMapping(value = "/listByProduct/{productId}", method = RequestMethod.GET)
    @ResponseBody
    //public CommonResult<List<Coupon>> listByProduct(@PathVariable Long productId) {
    public ResponseEntity<List<Coupon>>listByProduct(@PathVariable Long productId) {
        List<Coupon> couponList = couponService.listByProduct(productId);
        //return CommonResult.success(couponHistoryList);
        return ResponseEntity.ok(couponList);
    }

    ////////////////////////////////////////////////////////////////////////////
    //@ApiOperation("添加优惠券")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean> create(@RequestBody CouponParam couponParam) {
//    public CommonResult add(@RequestBody SmsCouponParam couponParam) {
        int count = couponService.create(couponParam);
        if(count>0){
            //return CommonResult.success(count);
            return ResponseEntity.ok(true);
        }
        //return CommonResult.failed();
        return ResponseEntity.ok(false);
    }

//    @ApiOperation("删除优惠券")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    //public CommonResult delete(@PathVariable Long id) {
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        int count = couponService.delete(id);
        if(count>0){
            return ResponseEntity.ok(true);
            //return CommonResult.success(count);
        }
        return ResponseEntity.ok(false);
        //return CommonResult.failed();
    }

    //@ApiOperation("修改优惠券")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Boolean>update(@PathVariable Long id,@RequestBody CouponParam couponParam) {
    //public CommonResult update(@PathVariable Long id,@RequestBody SmsCouponParam couponParam) {
        int count = couponService.update(id,couponParam);
        if(count>0){
            //return CommonResult.success(count);
            return ResponseEntity.ok(true);
        }
        //return CommonResult.failed();
        return ResponseEntity.ok(false);
    }

//    @ApiOperation("根据优惠券名称和类型分页获取优惠券列表")
//    @RequestMapping(value = "/list", method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult<CommonPage<SmsCoupon>> list(
//            @RequestParam(value = "name",required = false) String name,
//            @RequestParam(value = "type",required = false) Integer type,
//            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
//            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        List<SmsCoupon> couponList = couponService.list(name,type,pageSize,pageNum);
//        return CommonResult.success(CommonPage.restPage(couponList));
//    }
//
    //@ApiOperation("获取单个优惠券的详细信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    //public CommonResult<SmsCouponParam> getItem(@PathVariable Long id) {
    public ResponseEntity<CouponParam> getItem(@PathVariable Long id) {
        CouponParam couponParam = couponService.getItem(id);
        return ResponseEntity.ok(couponParam);
        //return CommonResult.success(couponParam);
    }
}
