package com.jiwell.favorite.service.impl;

import com.jiwell.auth.entity.UserInfo;
import com.jiwell.favorite.dao.CouponDao;
import com.jiwell.favorite.dao.CouponHistoryDao;
import com.jiwell.favorite.dao.CouponProductCategoryRelationDao;
import com.jiwell.favorite.dao.CouponProductRelationDao;
import com.jiwell.favorite.domain.CartPromotionItem;
import com.jiwell.favorite.domain.CouponHistoryDetail;
import com.jiwell.favorite.dto.CouponParam;
import com.jiwell.favorite.interceptor.LoginInterceptor;
import com.jiwell.favorite.mapper.CouponProductCategoryRelationMapper;
import com.jiwell.favorite.mapper.CouponProductRelationMapper;
import com.jiwell.favorite.mapper.Example.CouponExample;
import com.jiwell.favorite.mapper.Example.CouponHistoryExample;
import com.jiwell.favorite.mapper.Example.CouponProductCategoryRelationExample;
import com.jiwell.favorite.mapper.Example.CouponProductRelationExample;
import com.jiwell.favorite.pojo.*;
import com.jiwell.favorite.mapper.CouponHistoryMapper;
import com.jiwell.favorite.mapper.CouponMapper;
import com.jiwell.favorite.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员优惠券管理Service实现类
 * Created by macro on 2018/8/29.
 */
@Service
public class CouponServiceImpl implements CouponService {
//    @Autowired
//    private UmsMemberService memberService;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private CouponHistoryMapper couponHistoryMapper;
    @Autowired
    private CouponHistoryDao couponHistoryDao;
    /////////////////////////////////////////

    @Autowired
    private CouponProductRelationMapper productRelationMapper;
    @Autowired
    private CouponProductCategoryRelationMapper productCategoryRelationMapper;
    @Autowired
    private CouponProductRelationDao productRelationDao;
    @Autowired
    private CouponProductCategoryRelationDao productCategoryRelationDao;
    @Autowired
    private CouponDao couponDao;
//    /////////////////////////////////////////
    @Override
    public Boolean add(Long couponId) {
        //        UmsMember currentMember = memberService.getCurrentMember();
        //获取登录的用户
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        long userId = userInfo.getId();
        //获取优惠券信息，判断数量
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        if(coupon==null){
            //return CommonResult.failed("优惠券不存在");
            return false;
        }
        if(coupon.getCount()<=0){
            //return CommonResult.failed("优惠券已经领完了");
            return false;
        }
        Date now = new Date();
        if(now.before(coupon.getEnableTime())){
            //return CommonResult.failed("优惠券还没到领取时间");
            return false;
        }
        //Example example = new Example(CouponHistory.class);
        //example.createCriteria().andEqualTo("couponId",couponId).andEqualTo("member_id",userId);
        //判断用户领取的优惠券数量是否超过限制
        CouponHistoryExample couponHistoryExample = new CouponHistoryExample();
        couponHistoryExample.createCriteria().andCouponIdEqualTo(couponId).andMemberIdEqualTo(userId);
        //kun 加的，要看看
        //long count = couponHistoryMapper.selectCountByExample(couponHistoryExample); //.countByExample(couponHistoryExample);
        long count = couponHistoryMapper.countByExample(couponHistoryExample);
        if(count>=coupon.getPerLimit()){
            //return CommonResult.failed("您已经领取过该优惠券");
            return false;
        }
        //生成领取优惠券历史
        CouponHistory couponHistory = new CouponHistory();
        couponHistory.setCouponId(couponId);
        couponHistory.setCouponCode(generateCouponCode(userId/*currentMember.getId()*/));//Kun
        couponHistory.setCreateTime(now);
        couponHistory.setMemberId(userId/*currentMember.getId()*/);
        couponHistory.setMemberNickname("nickname"/*currentMember.getNickname()*/);
        //主动领取
        couponHistory.setGetType(1);
        //未使用
        couponHistory.setUseStatus(0);
        couponHistoryMapper.insert(couponHistory);
        //修改优惠券表的数量、领取数量
        coupon.setCount(coupon.getCount()-1);
        coupon.setReceiveCount(coupon.getReceiveCount()==null?1:coupon.getReceiveCount()+1);
        couponMapper.updateByPrimaryKey(coupon);
        return true;
        //return CommonResult.success(null,"领取成功");
        //return null;
    }

    /**
     * 16位优惠码生成：时间戳后8位+4位随机数+用户id后4位
     */
    private String generateCouponCode(Long memberId) {
        StringBuilder sb = new StringBuilder();
        Long currentTimeMillis = System.currentTimeMillis();
        String timeMillisStr = currentTimeMillis.toString();
        sb.append(timeMillisStr.substring(timeMillisStr.length() - 8));
        for (int i = 0; i < 4; i++) {
            sb.append(new Random().nextInt(10));
        }
        String memberIdStr = memberId.toString();
        if (memberIdStr.length() <= 4) {
            sb.append(String.format("%04d", memberId));
        } else {
            sb.append(memberIdStr.substring(memberIdStr.length()-4));
        }
        return sb.toString();
    }

    @Override
    public List<CouponHistory> listHistory(Integer useStatus) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        long userId = userInfo.getId();
        CouponHistoryExample couponHistoryExample=new CouponHistoryExample();
        CouponHistoryExample.Criteria criteria = couponHistoryExample.createCriteria();
        criteria.andMemberIdEqualTo(userId);
        if(useStatus!=null){
            criteria.andUseStatusEqualTo(useStatus);
        }
        return couponHistoryMapper.selectByExample(couponHistoryExample);
    }

    @Override
    public List<Coupon> list(Integer useStatus) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        long userId = userInfo.getId();
        //UmsMember member = memberService.getCurrentMember();
        return couponHistoryDao.getCouponList(userId,useStatus);
    }

    @Override
    public List<CouponHistoryDetail> listCart(List<CartPromotionItem> cartItemList, Integer type) {

        UserInfo userInfo = LoginInterceptor.getLoginUser();
        long userId = userInfo.getId();
        Date now = new Date();
        //获取该用户所有优惠券
        List<CouponHistoryDetail> allList = couponHistoryDao.getDetailList(userId);
//        //根据优惠券使用类型来判断优惠券是否可用
        List<CouponHistoryDetail> enableList = new ArrayList<>();
        List<CouponHistoryDetail> disableList = new ArrayList<>();
        for (CouponHistoryDetail couponHistoryDetail : allList) {
            Integer useType = couponHistoryDetail.getCoupon().getUseType();
            BigDecimal minPoint = couponHistoryDetail.getCoupon().getMinPoint();
            Date endTime = couponHistoryDetail.getCoupon().getEndTime();
            if(useType.equals(0)){
                //0->全场通用
                //判断是否满足优惠起点
                //计算购物车商品的总价
                BigDecimal totalAmount = calcTotalAmount(cartItemList);
                if(now.before(endTime)&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(1)){
                //1->指定分类
                //计算指定分类商品的总价
                List<Long> productCategoryIds = new ArrayList<>();
                for (CouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                    productCategoryIds.add(categoryRelation.getProductCategoryId());
                }
                BigDecimal totalAmount = calcTotalAmountByproductCategoryId(cartItemList,productCategoryIds);
                if(now.before(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(2)){
                //2->指定商品
                //计算指定商品的总价
                List<Long> productIds = new ArrayList<>();
                for (CouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                    productIds.add(productRelation.getProductId());
                }
                BigDecimal totalAmount = calcTotalAmountByProductId(cartItemList,productIds);
                if(now.before(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }
        }
        if(type.equals(1)){//可用的優惠卷
            return enableList;
        }else{
            return disableList;
        }
    }

    @Override
    public List<Coupon> listByProduct(Long productId) {
        List<Long> allCouponIds = new ArrayList<>();
        //获取指定商品优惠券
        CouponProductRelationExample cprExample = new CouponProductRelationExample();
        cprExample.createCriteria().andProductIdEqualTo(productId);
        List<CouponProductRelation> cprList = productRelationMapper.selectByExample(cprExample);
        //if(CollUtil.isNotEmpty(cprList)){
        if(cprList.size() > 0){
            List<Long> couponIds = cprList.stream().map(CouponProductRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //获取指定分类优惠券
        //kun 修改
        long categoryId = 16;
        //Product product = productMapper.selectByPrimaryKey(productId);
        CouponProductCategoryRelationExample cpcrExample = new CouponProductCategoryRelationExample();
        cpcrExample.createCriteria().andProductCategoryIdEqualTo(categoryId/*product.getProductCategoryId()*/);
        List<CouponProductCategoryRelation> cpcrList = productCategoryRelationMapper.selectByExample(cpcrExample);
        //if(CollUtil.isNotEmpty(cpcrList)){
        if(cprList.size() > 0){
            List<Long> couponIds = cpcrList.stream().map(CouponProductCategoryRelation::getCouponId).collect(Collectors.toList());
            allCouponIds.addAll(couponIds);
        }
        //if(CollUtil.isEmpty(allCouponIds)){
        if(cprList.size() <= 0){
            return new ArrayList<>();
        }
        //所有优惠券
        CouponExample couponExample = new CouponExample();
        couponExample.createCriteria().andEndTimeGreaterThan(new Date())
                .andStartTimeLessThan(new Date())
                .andUseTypeEqualTo(0);
        couponExample.or(couponExample.createCriteria()
                .andEndTimeGreaterThan(new Date())
                .andStartTimeLessThan(new Date())
                .andUseTypeNotEqualTo(0)
                .andIdIn(allCouponIds));
        return couponMapper.selectByExample(couponExample);
    }

    private BigDecimal calcTotalAmount(List<CartPromotionItem> cartItemList) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
            BigDecimal price =new BigDecimal(item.getPrice());
            BigDecimal realPrice = price.subtract(item.getReduceAmount());
            total=total.add(realPrice.multiply(new BigDecimal(item.getNum())));
        }
        return total;
    }

    private BigDecimal calcTotalAmountByproductCategoryId(List<CartPromotionItem> cartItemList,List<Long> productCategoryIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
//            if(productCategoryIds.contains(item.getProductCategoryId())){
//                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
//                total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
//            }
        }
        return total;
    }

    private BigDecimal calcTotalAmountByProductId(List<CartPromotionItem> cartItemList,List<Long> productIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartItemList) {
            if(productIds.contains(item.getSkuId())){
                BigDecimal price =new BigDecimal(item.getPrice());
                BigDecimal realPrice = price.subtract(item.getReduceAmount());
                total=total.add(realPrice.multiply(new BigDecimal(item.getNum())));
            }
        }
        return total;
    }

    //以下為管理介界使用
    public int create(CouponParam couponParam) {
        couponParam.setCount(couponParam.getPublishCount());
        couponParam.setUseCount(0);
        couponParam.setReceiveCount(0);
        //插入优惠券表
        int count = couponMapper.insert(couponParam);
        //插入优惠券和商品关系表
        if(couponParam.getUseType().equals(2)){
            for(CouponProductRelation productRelation:couponParam.getProductRelationList()){
                productRelation.setCouponId(couponParam.getId());
            }
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //插入优惠券和商品分类关系表
        if(couponParam.getUseType().equals(1)){
            for (CouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        //删除优惠券
        int count = couponMapper.deleteByPrimaryKey(id);
        //删除商品关联
        deleteProductRelation(id);
        //删除商品分类关联
        deleteProductCategoryRelation(id);
        return count;
    }

    private void deleteProductCategoryRelation(Long id) {
        CouponProductCategoryRelationExample productCategoryRelationExample = new CouponProductCategoryRelationExample();
        productCategoryRelationExample.createCriteria().andCouponIdEqualTo(id);
        productCategoryRelationMapper.deleteByExample(productCategoryRelationExample);
    }

    private void deleteProductRelation(Long id) {
        CouponProductRelationExample productRelationExample = new CouponProductRelationExample();
        productRelationExample.createCriteria().andCouponIdEqualTo(id);
        productRelationMapper.deleteByExample(productRelationExample);
    }

    @Override
    public int update(Long id, CouponParam couponParam) {
        couponParam.setId(id);
        int count =couponMapper.updateByPrimaryKey(couponParam);
        //删除后插入优惠券和商品关系表
        if(couponParam.getUseType().equals(2)){
            for(CouponProductRelation productRelation:couponParam.getProductRelationList()){
                productRelation.setCouponId(couponParam.getId());
            }
            deleteProductRelation(id);
            productRelationDao.insertList(couponParam.getProductRelationList());
        }
        //删除后插入优惠券和商品分类关系表
        if(couponParam.getUseType().equals(1)){
            for (CouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            deleteProductCategoryRelation(id);
            productCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }
//
//    @Override
//    public List<Coupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
//        CouponExample example = new CouponExample();
//        CouponExample.Criteria criteria = example.createCriteria();
//        if(!StringUtils.isEmpty(name)){
//            criteria.andNameLike("%"+name+"%");
//        }
//        if(type!=null){
//            criteria.andTypeEqualTo(type);
//        }
//        PageHelper.startPage(pageNum,pageSize);
//        return couponMapper.selectByExample(example);
//    }
//
    @Override
    public CouponParam getItem(Long id) {
        return couponDao.getItem(id);
    }
}
