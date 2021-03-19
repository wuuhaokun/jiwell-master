package com.jiwell.response;

/**
 * @Author: 98050
 * @Time: 2018-11-24 21:37
 * @Feature: 返回狀態碼
 */
public class CodeMsg {

    private int code;
    private String msg;


    /**
     * 通用的錯誤碼
     */
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服務端異常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "參數校驗異常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "請求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED= new CodeMsg(500104, "訪問太頻繁！");

    /**
     * 登錄模塊 5002XX
     */
    public static CodeMsg LOGIN_ERROR = new CodeMsg(500210, "用戶未登錄");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登錄密碼不能為空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手機號不能為空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手機號格式錯誤");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手機號不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密碼錯誤");


    //商品模塊 5003XX


    /**
     * 訂單模塊 5004XX
     */
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "訂單不存在");

    /**
     * 秒殺模塊 5005XX
     */
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已經秒殺完畢");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "不能重複秒殺");
    public static CodeMsg MIAOSHA_FAIL = new CodeMsg(500502, "秒殺失敗");


    private CodeMsg( ) {
    }

    private CodeMsg( int code,String msg ) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }
}
