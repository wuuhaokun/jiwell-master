package com.jiwell.auth.entity;

/**
 * Token信息
 */
public class TokenInfo {

    private String token;

    public TokenInfo() {
    }

    public TokenInfo(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
