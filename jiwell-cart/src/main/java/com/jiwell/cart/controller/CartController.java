package com.jiwell.cart.controller;

import com.jiwell.cart.pojo.Cart;
import com.jiwell.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-25 20:41
 * @Feature:
 */
@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 添加購物車
     * @param cart
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody Cart cart){
        this.cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 查詢購物車
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Cart>> queryCartList(){
        List<Cart> carts = this.cartService.queryCartList();
        if(carts == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改購物車中商品數量
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateNum(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num){
        this.cartService.updateNum(skuId,num);
        return ResponseEntity.ok().build();
    }

    /**
     * 刪除購物車中的商品
     * @param skuId
     * @return
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") String skuId){
        this.cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }

}

