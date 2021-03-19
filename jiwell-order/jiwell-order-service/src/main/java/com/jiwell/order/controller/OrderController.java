package com.jiwell.order.controller;

import com.jiwell.common.pojo.PageResult;
import com.jiwell.order.pojo.Order;
import com.jiwell.order.pojo.OrderStatus;
import com.jiwell.order.service.OrderService;
import com.jiwell.order.utils.PayHelper;

import com.jiwell.order.utils.PayState;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-27 16:30
 * @Feature: 訂單Controller
 */
@RestController
@RequestMapping("order")
@Api("訂單服務接口")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayHelper payHelper;

    private static int count = 0;

    /**
     * 創建訂單
     * @param order 訂單對象
     * @return 訂單編號
     */
    @PostMapping
    @ApiOperation(value = "創建訂單接口，返回訂單編號",notes = "創建訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order",required = true,value = "訂單的json對象，包含訂單條目和物流信息"),
    })
    public ResponseEntity<List<Long>> createOrder(@RequestBody Order order){
        List<Long> skuId = this.orderService.queryStock(order);
        if (skuId.size() != 0){
            //库存不足
            return new ResponseEntity<>(skuId,HttpStatus.OK);
        }

        Long id = this.orderService.createOrder(order);
        return new ResponseEntity<>(Arrays.asList(id), HttpStatus.CREATED);
    }
    /**
     * 刪除訂單
     * @param orderId
     * @return
     */
    @DeleteMapping("{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("orderId") long orderId){
        Boolean result = this.orderService.deleteOrder(orderId);
        if (!result){
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 查詢訂單
     * @param id 訂單編號
     * @return 訂單對象
     */
    @ApiOperation(value = "根據訂單編號查詢訂單，返回訂單對象",notes = "查詢訂單")
    @ApiImplicitParam(name = "id",required = true,value = "訂單編號",type = "Long")
    @GetMapping("{id}")
    public ResponseEntity<Order> queryOrderById(@PathVariable("id") Long id){
        System.out.println("查詢訂單："+id);
        Order order = this.orderService.queryOrderById(id);
        if (order == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(order);
    }

    /**
     * 分頁查詢當前已經登錄的用戶訂單
     * @param page 頁數
     * @param rows 每頁大小
     * @param status 訂單狀態
     * @return
     */
    @GetMapping("list")
    @ApiOperation(value = "分頁查詢當前用戶訂單，並且可以根據訂單狀態過濾",notes = "分頁查詢當前用戶訂單")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "當前頁",defaultValue = "1",type = "Integer"),
            @ApiImplicitParam(name = "rows",value = "每頁大小",defaultValue = "5",type = "Integer"),
            @ApiImplicitParam(name = "status",value = "訂單狀態：1未付款，" +
                    "2已付款未發貨，" +
                    "3已發貨未確認，" +
                    "4已確認未評價，" +
                    "5交易關閉，" +
                    "6交易成功，已評價",defaultValue = "1",type = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "訂單的分頁結果"),
            @ApiResponse(code = 404, message = "沒有查詢到結果"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<PageResult<Order>> queryUserOrderList(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "status",required = false)Integer status
    ){

        PageResult<Order> result = this.orderService.queryUserOrderList(page,rows,status);
        if (result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }


    /**
     * 更新訂單狀態
     * @param id
     * @param status
     * @return
     */
    @PutMapping("{id}/{status}")
    @ApiOperation(value = "更新訂單狀態",notes = "更新訂單狀態")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "訂單編號",type = "Long"),
            @ApiImplicitParam(name = "status",value = "訂單狀態：1未付款，" +
                    "2已付款未發貨，" +
                    "3已發貨未確認，" +
                    "4已確認未評價，" +
                    "5交易關閉，" +
                    "6交易成功，已評價",defaultValue = "1",type = "Integer")
    })
    @ApiResponses({
            @ApiResponse(code = 204,message = "true:修改成功；false:修改狀態失敗"),
            @ApiResponse(code = 400,message = "請求參數有誤"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable("id") Long id,@PathVariable("status") Integer status){
        Boolean result = this.orderService.updateOrderStatus(id,status);
        if (!result){
            //返回400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //返回204
        //這里返回204會有問題,Body 會為空，所以改為使用HttpStatus.OK 可參考https://blog.csdn.net/u010234516/article/details/103600587
        //return new ResponseEntity<>(true,HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(true,HttpStatus.OK);

    }

    /**
     * 根據訂單id生成付款鏈接
     * @param orderId
     * @return
     */
    @GetMapping("url/{id}")
    @ApiOperation(value = "生成微信掃描支付付款鏈接",notes = "生成付款鏈接")
    @ApiImplicitParam(name = "id",value = "訂單編號",type = "Long")
    @ApiResponses({
            @ApiResponse(code = 200,message = "根據訂單編號生成的微信支付地址"),
            @ApiResponse(code = 404,message = "生成鏈接失敗"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<String> generateUrl(@PathVariable("id") Long orderId){
        String url = this.payHelper.createPayUrl(orderId);
        if (StringUtils.isNotBlank(url)){
            return ResponseEntity.ok(url);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * 查詢付款狀態
     * @param orderId
     * @return
     */
    @GetMapping("state/{id}")
    @ApiOperation(value = "查詢掃碼支付的付款狀態",notes = "查詢付款狀態")
    @ApiImplicitParam(name = "id",value = "訂單編號",type = "Long")
    @ApiResponses({
            @ApiResponse(code = 200, message = "0, 未查詢到支付信息 1,支付成功 2,支付失敗"),
            @ApiResponse(code = 500, message = "服務器異常"),
    })
    public ResponseEntity<Integer> queryPayState(@PathVariable("id") Long orderId){
        PayState payState = this.payHelper.queryOrder(orderId);
        return ResponseEntity.ok(payState.getValue());
    }

    /**
     * 根據訂單id查詢其包含的skuId
     * @param id
     * @return
     */
    @GetMapping("skuId/{id}")
    @ApiOperation(value = "根據訂單號查詢其包含的所有商品ID",notes = "查詢商品ID")
    @ApiImplicitParam(name = "id",value = "訂單編號",type = "Long")
    @ApiResponses({
            @ApiResponse(code = 200,message = "商品訂單號集合"),
            @ApiResponse(code = 404,message = "沒有找到對應的訂單號集合"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<List<Long>> querySkuIdByOrderId(@PathVariable("id") Long id){
        List<Long> longList = this.orderService.querySkuIdByOrderId(id);
        if (longList == null || longList.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(longList);
    }


    /**
     * 根據訂單id查詢訂單狀態
     * @param id
     * @return
     */
    @GetMapping("status/{id}")
    @ApiOperation(value = "根據訂單號查詢訂單狀態",notes = "查詢訂單狀態")
    @ApiImplicitParam(name = "id",value = "訂單編號",type = "Long")
    @ApiResponses({
            @ApiResponse(code = 200,message = "訂單狀態"),
            @ApiResponse(code = 404,message = "沒有找到對應的訂單狀態"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<OrderStatus> queryOrderStatusById(@PathVariable("id") Long id){
        OrderStatus orderStatus = this.orderService.queryOrderStatusById(id);
        if (orderStatus == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(orderStatus);
    }
}
