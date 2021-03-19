package com.jiwell.cart.service;

import com.jiwell.cart.pojo.Cart;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-25 20:47
 * @Feature:
 */
public interface CartService {
    /**
     * 添加購物車
     * @param cart
     */
    void addCart(Cart cart);

    /**
     * 查詢購物車
     * @return
     */
    List<Cart> queryCartList();

    /**
     * 更新購物車中商品數量
     * @param skuId
     * @param num
     */
    void updateNum(Long skuId, Integer num);

    /**
     * 刪除購物車中的商品
     * @param skuId
     */
    void deleteCart(String skuId);
}
