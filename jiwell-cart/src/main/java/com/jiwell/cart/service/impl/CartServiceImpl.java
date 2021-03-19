package com.jiwell.cart.service.impl;

import com.jiwell.auth.entity.UserInfo;
import com.jiwell.cart.client.GoodsClient;
import com.jiwell.cart.interceptor.LoginInterceptor;
import com.jiwell.cart.pojo.Cart;
import com.jiwell.cart.service.CartService;
import com.jiwell.item.pojo.Sku;
import com.jiwell.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 98050
 * @Time: 2018-10-25 20:48
 * @Feature:
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Autowired
//    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    private static String KEY_PREFIX = "jiwell:cart:uid:";

    private final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    /**
     * 添加購物車
     * @param cart
     */
    @Override
    public void addCart(Cart cart) {
        //1.獲取用戶
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        //2.Redis的key
        String key = KEY_PREFIX + userInfo.getId();
        //3.獲取hash操作對象
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //4.查詢是否存在
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean result = hashOperations.hasKey(skuId.toString());
        if (result){
            //5.存在，獲取購物車數據
            String json = hashOperations.get(skuId.toString()).toString();
            cart = JsonUtils.parse(json,Cart.class);
            //6.修改購物車數量
            cart.setNum(cart.getNum() + num);
        }else{
            //7.不存在，新增購物車數據
            cart.setUserId(userInfo.getId());
            //8.其他商品信息，需要查詢商品微服務
            Sku sku = this.goodsClient.querySkuById(skuId);
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(),",")[0]);
            cart.setPrice(sku.getPrice());
            cart.setTitle(sku.getTitle());
            cart.setOwnSpec(sku.getOwnSpec());
        }
        //9.將購物車數據寫入redis
        hashOperations.put(cart.getSkuId().toString(),JsonUtils.serialize(cart));
    }

    /**
     * 查詢購物車
     * @return
     */
    @Override
    public List<Cart> queryCartList() {
        //1.獲取登錄的用戶信息
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        //2.判斷是否存在購物車
        String key = KEY_PREFIX + userInfo.getId();
        if (!this.stringRedisTemplate.hasKey(key)) {
            //3.不存在直接返回
            return null;
        }
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        List<Object> carts = hashOperations.values();
        //4.判斷是否有數據
        if (CollectionUtils.isEmpty(carts)){
            return null;
        }
        //5.查詢購物車數據
        return carts.stream().map( o -> JsonUtils.parse(o.toString(),Cart.class)).collect(Collectors.toList());
    }

    /**
     * 更新購物車中商品數量
     * @param skuId
     * @param num
     */
    @Override
    public void updateNum(Long skuId, Integer num) {
        //1.獲取登錄用戶
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //2.獲取購物車
        String json = hashOperations.get(skuId.toString()).toString();
        Cart cart = JsonUtils.parse(json,Cart.class);
        cart.setNum(num);
        //3.寫入購物車
        hashOperations.put(skuId.toString(),JsonUtils.serialize(cart));
    }

    /**
     * 刪除購物車中的商品
     * @param skuId
     */
    @Override
    public void deleteCart(String skuId) {
        //1.獲取登錄用戶
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        String key = KEY_PREFIX + userInfo.getId();
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(key);
        //2.刪除商品
        hashOperations.delete(skuId);
    }
}
