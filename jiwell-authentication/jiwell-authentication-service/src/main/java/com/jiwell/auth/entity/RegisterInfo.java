package com.jiwell.auth.entity;

import com.jiwell.user.utils.RegisterState;

/**
 * 用户信息
 */
public class RegisterInfo {

    private RegisterState registerState;

    private String token;

    public RegisterInfo() {
    }

    public RegisterInfo(RegisterState registerState, String token) {
        this.registerState = registerState;
        this.token = token;
    }

    public RegisterState getRegisterState() {
        return this.registerState;
    }

    public void setRegisterState(RegisterState registerState) {
        this.registerState = registerState;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
