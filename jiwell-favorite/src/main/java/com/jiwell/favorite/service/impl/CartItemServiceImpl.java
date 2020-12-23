package com.jiwell.favorite.service.impl;

//import com.macro.mall.mapper.OmsCartItemMapper;
//import com.macro.mall.model.OmsCartItem;
//import com.macro.mall.model.OmsCartItemExample;
//import com.macro.mall.model.UmsMember;
//import com.macro.mall.portal.dao.PortalProductDao;
//import com.macro.mall.portal.domain.CartProduct;
//import com.macro.mall.portal.domain.CartPromotionItem;
//import com.macro.mall.portal.service.OmsCartItemService;
//import com.macro.mall.portal.service.OmsPromotionService;
//import com.macro.mall.portal.service.UmsMemberService;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.favorite.domain.Cart;
import com.jiwell.favorite.domain.CartPromotionItem;
import com.jiwell.favorite.interceptor.LoginInterceptor;
import com.jiwell.favorite.service.CartItemService;
import com.jiwell.favorite.service.PromotionService;
import com.jiwell.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
//
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车管理Service实现类
 * Created by macro on 2018/8/2.
 */
@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String KEY_PREFIX = "jiwell:cart:uid:";

//    @Autowired
//    private OmsCartItemMapper cartItemMapper;
//    @Autowired
//    private PortalProductDao productDao;
    @Autowired
    private PromotionService promotionService;
//    @Autowired
//    private UmsMemberService memberService;

//    @Override
//    public int add(OmsCartItem cartItem) {
//        int count;
//        UmsMember currentMember =memberService.getCurrentMember();
//        cartItem.setMemberId(currentMember.getId());
//        cartItem.setMemberNickname(currentMember.getNickname());
//        cartItem.setDeleteStatus(0);
//        OmsCartItem existCartItem = getCartItem(cartItem);
//        if (existCartItem == null) {
//            cartItem.setCreateDate(new Date());
//            count = cartItemMapper.insert(cartItem);
//        } else {
//            cartItem.setModifyDate(new Date());
//            existCartItem.setQuantity(existCartItem.getQuantity() + cartItem.getQuantity());
//            count = cartItemMapper.updateByPrimaryKey(existCartItem);
//        }
//        return count;
//    }
//
//    /**
//     * 根据会员id,商品id和规格获取购物车中商品
//     */
//    private OmsCartItem getCartItem(OmsCartItem cartItem) {
//        OmsCartItemExample example = new OmsCartItemExample();
//        OmsCartItemExample.Criteria criteria = example.createCriteria().andMemberIdEqualTo(cartItem.getMemberId())
//                .andProductIdEqualTo(cartItem.getProductId()).andDeleteStatusEqualTo(0);
//        if (!StringUtils.isEmpty(cartItem.getSp1())) {
//            criteria.andSp1EqualTo(cartItem.getSp1());
//        }
//        if (!StringUtils.isEmpty(cartItem.getSp2())) {
//            criteria.andSp2EqualTo(cartItem.getSp2());
//        }
//        if (!StringUtils.isEmpty(cartItem.getSp3())) {
//            criteria.andSp3EqualTo(cartItem.getSp3());
//        }
//        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
//        if (!CollectionUtils.isEmpty(cartItemList)) {
//            return cartItemList.get(0);
//        }
//        return null;
//    }
//
    @Override
    public List<Cart> list(Long memberId) {
        //1.获取登录的用户信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        //2.判断是否存在购物车
        String key = KEY_PREFIX + userInfo.getId();
        if (!this.stringRedisTemplate.hasKey(key)) {
            //3.不存在直接返回
            return null;
        }
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        List<Object> carts = hashOperations.values();
        //4.判断是否有数据
        if (CollectionUtils.isEmpty(carts)){
            return null;
        }
        //5.查询购物车数据
        return carts.stream().map( o -> JsonUtils.parse(o.toString(),Cart.class)).collect(Collectors.toList());
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long memberId) {
        List<Cart> cartItemList = list(memberId);
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartItemList)){
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

//    @Override
//    public int updateQuantity(Long id, Long memberId, Integer quantity) {
//        OmsCartItem cartItem = new OmsCartItem();
//        cartItem.setQuantity(quantity);
//        OmsCartItemExample example = new OmsCartItemExample();
//        example.createCriteria().andDeleteStatusEqualTo(0)
//                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
//        return cartItemMapper.updateByExampleSelective(cartItem, example);
//    }
//
//    @Override
//    public int delete(Long memberId, List<Long> ids) {
//        OmsCartItem record = new OmsCartItem();
//        record.setDeleteStatus(1);
//        OmsCartItemExample example = new OmsCartItemExample();
//        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId);
//        return cartItemMapper.updateByExampleSelective(record, example);
//    }
//
//    @Override
//    public CartProduct getCartProduct(Long productId) {
//        return productDao.getCartProduct(productId);
//    }
//
//    @Override
//    public int updateAttr(OmsCartItem cartItem) {
//        //删除原购物车信息
//        OmsCartItem updateCart = new OmsCartItem();
//        updateCart.setId(cartItem.getId());
//        updateCart.setModifyDate(new Date());
//        updateCart.setDeleteStatus(1);
//        cartItemMapper.updateByPrimaryKeySelective(updateCart);
//        cartItem.setId(null);
//        add(cartItem);
//        return 1;
//    }
//
//    @Override
//    public int clear(Long memberId) {
//        OmsCartItem record = new OmsCartItem();
//        record.setDeleteStatus(1);
//        OmsCartItemExample example = new OmsCartItemExample();
//        example.createCriteria().andMemberIdEqualTo(memberId);
//        return cartItemMapper.updateByExampleSelective(record,example);
//    }
}
