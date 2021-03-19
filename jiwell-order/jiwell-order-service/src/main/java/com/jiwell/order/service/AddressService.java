package com.jiwell.order.service;

import com.jiwell.order.pojo.Address;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-31 09:43
 * @Feature:
 */
public interface AddressService {
    /**
     * 刪除地址
     * @param addressId
     */
    void deleteAddress(Long addressId);

    /**
     * 更新地址
     * @param address
     */
    void updateAddressByUserId(Address address);

    /**
     * 查詢地址
     * @return
     */
    List<Address> queryAddressByUserId();

    /**
     * 新增收貨地址
     * @param address
     */
    void addAddressByUserId(Address address);

    /**
     * 根據地址id查詢地址
     * @param addressId
     * @return
     */
    Address queryAddressById(Long addressId);
}
