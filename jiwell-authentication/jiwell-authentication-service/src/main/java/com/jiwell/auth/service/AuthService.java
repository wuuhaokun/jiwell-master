package com.jiwell.auth.service;

import com.jiwell.auth.entity.RegisterInfo;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:46
 * @Feature:
 */
public interface AuthService {
    /**
     * 用户授权
     * @param account
     * @param password
     * @return
     */
    String authentication(String account, String password);

    RegisterInfo authenticationAndRegister(String account, String password);

}
