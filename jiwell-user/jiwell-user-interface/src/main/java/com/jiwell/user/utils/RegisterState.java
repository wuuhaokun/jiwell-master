package com.jiwell.user.utils;

public enum RegisterState {
    /**
     * 註冊成功 0
     * 己存在   1
     * 註冊失敗 2
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
