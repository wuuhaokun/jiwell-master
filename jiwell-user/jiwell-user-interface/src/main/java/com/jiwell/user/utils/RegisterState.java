package com.jiwell.user.utils;

public enum RegisterState {
    /**
     * 未支付0
     * 支付成功1
     * 支付失败2
     */
    REGISTER(0),EXIST(1),FAIL(2);

    RegisterState(int value) {
        this.value = value;
    }

    int value;

    public int getValue() {
        return value;
    }
}
