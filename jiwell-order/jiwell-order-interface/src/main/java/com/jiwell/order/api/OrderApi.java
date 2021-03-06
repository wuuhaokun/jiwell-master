package com.jiwell.order.api;

import com.jiwell.order.pojo.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-11-12 15:13
 * @Feature: 訂單服務接口
 */
@RequestMapping("order")
public interface OrderApi {

    /**
     * 創建訂單
     * @param seck
     * @param order
     * @return
     */
    @PostMapping
    ResponseEntity<List<Long>> createOrder(@RequestParam("seck") String seck, @RequestBody @Valid Order order);


    /**
     * 修改訂單狀態
     * @param id
     * @param status
     * @return
     */
    @PutMapping("{id}/{status}")
    ResponseEntity<Boolean> updateOrderStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status);
}
