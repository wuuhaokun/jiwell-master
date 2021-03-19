package com.jiwell.order.controller;

import com.jiwell.order.pojo.Address;
import com.jiwell.order.service.AddressService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-31 09:44
 * @Feature: 地址CRUD
 */
@RestController
@RequestMapping("address")
@Api("地址管理")
public class AddressController {

    @Autowired
    private AddressService addressService;

    /**
     * 創建收貨地址
     * @return
     */
    @PostMapping
    @ApiOperation(value = "創建收貨地址接口",notes = "創建地址")
    @ApiImplicitParam(name = "address",required = true,value = "地址對象")
    @ApiResponses({
            @ApiResponse(code = 201, message = "地址創建成功"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<Void> addAddressByUserId(@RequestBody @Valid Address address){
        System.out.println(address.getDefaultAddress());
        this.addressService.addAddressByUserId(address);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根據用戶id查詢地址列表
     * @return
     */
    @GetMapping
    @ApiOperation(value = "查詢收貨地址接口，返回地址列表",notes = "查詢地址")
    @ApiResponses({
            @ApiResponse(code = 200, message = "地址列表"),
            @ApiResponse(code = 404,message = "沒有查詢到結果"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<List<Address>> queryAddressByUserId(){
        List<Address> addresses = this.addressService.queryAddressByUserId();
        if (addresses == null || addresses.size() == 0){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(addresses);
    }

    /**
     * 修改收貨地址
     * @param address
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改收貨地址接口",notes = "修改地址")
    @ApiImplicitParam(name = "address", required=true, value = "地址對象")
    @ApiResponses({
            @ApiResponse(code = 204, message = "修改成功"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<Void> updateAddressByUserId(@RequestBody Address address){
        this.addressService.updateAddressByUserId(address);
        System.out.println("controller");
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 刪除收貨地址
     * @param addressId
     * @return
     */
    @DeleteMapping("{addressId}")
    @ApiOperation(value = "刪除收貨地址接口",notes = "創建地址")
    @ApiImplicitParam(name = "addressId", required=true, value = "地址id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "地址刪除成功"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<Void> deleteAddress(@PathVariable("addressId") Long addressId){
        this.addressService.deleteAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("{addressId}")
    @ApiOperation(value = "根據id查詢收貨地址接口",notes = "查詢地址")
    @ApiImplicitParam(name = "addressId", required=true, value = "地址id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "查詢成功"),
            @ApiResponse(code = 404, message = "查詢失敗"),
            @ApiResponse(code = 500,message = "服務器異常")
    })
    public ResponseEntity<Address> queryAddressById(@PathVariable("addressId") Long addressId){
        Address address = this.addressService.queryAddressById(addressId);
        if (address == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(address);
    }
}
