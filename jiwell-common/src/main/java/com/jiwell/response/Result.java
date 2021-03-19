package com.jiwell.response;

/**
 * @Author: 98050
 * @Time: 2018-11-24 21:41
 * @Feature: 返回結果
 */
public class Result<T> {

    private int code;
    private String msg;
    private T data;

    /**
     * 成功時候的調用
     * */
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>(data);
        result.code = CodeMsg.SUCCESS.getCode();
        return new Result<T>(data);
    }

    /**
     * 失敗時候的調用
     * */
    public static  <T> Result<T> error(CodeMsg codeMsg){
        return new Result<T>(codeMsg);
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(CodeMsg codeMsg) {
        if(codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
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
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}
