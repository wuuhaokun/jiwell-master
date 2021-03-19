package com.jiwell.order.service.impl;

import com.jiwell.auth.entity.UserInfo;
import com.jiwell.order.interceptor.LoginInterceptor;
import com.jiwell.order.mapper.AddressMapper;
import com.jiwell.order.pojo.Address;
import com.jiwell.order.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-31 09:44
 * @Feature:
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public void deleteAddress(Long addressId) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo("userId",userInfo.getId()).andEqualTo("id",addressId);
        this.addressMapper.deleteByExample(example);
    }

    @Override
    public void updateAddressByUserId(Address address) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        address.setUserId(userInfo.getId());
        setDefaultAddress(address);
        this.addressMapper.updateByPrimaryKeySelective(address);

    }

    @Override
    public List<Address> queryAddressByUserId() {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo("userId",userInfo.getId());
        return this.addressMapper.selectByExample(example);
    }

    @Override
    public void addAddressByUserId(Address address) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        address.setUserId(userInfo.getId());
        setDefaultAddress(address);
        this.addressMapper.insert(address);
    }

    @Override
    public Address queryAddressById(Long addressId) {
        UserInfo userInfo = LoginInterceptor.getLoginUser();
        Example example = new Example(Address.class);
        example.createCriteria().andEqualTo("id",addressId).andEqualTo("userId",userInfo.getId());
        return this.addressMapper.selectByExample(example).get(0);
    }

    public void setDefaultAddress(Address address){
        if (address.getDefaultAddress()){
            //如果將本地址設置為默認地址，那麼該用戶下的其他地址都應該是非默認地址
            List<Address> addressList = this.queryAddressByUserId();
            addressList.forEach(addressTemp -> {
                if (addressTemp.getDefaultAddress()){
                    addressTemp.setDefaultAddress(false);
                    this.addressMapper.updateByPrimaryKeySelective(addressTemp);
                }
            } );
        }
    }
}
