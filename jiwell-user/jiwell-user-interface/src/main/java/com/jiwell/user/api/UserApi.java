package com.jiwell.user.api;

import com.jiwell.user.pojo.User;
import com.jiwell.user.utils.RegisterState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 98050
 * @Time: 2018-10-23 23:50
 * @Feature: 用戶服務接口
 */
public interface UserApi {
    /**
     * 用戶驗證
     * @param account
     * @param password
     * @return
     */
    @GetMapping("query")
    User queryUser(@RequestParam("account")String account, @RequestParam("password")String password);

    /**
     * 使用驗證碼登入並註冊
     * @param account
     * @param password
     * @return
     */
    @PostMapping("loginAndRegister")
    RegisterState loginAndRegister(@RequestParam("account")String account, @RequestParam("password")String password);

    /**
     * 取得使用者資料
     * @param account
     * @return
     */
    @GetMapping("queryInfo")
    User queryUserByAccount(@RequestParam("account")String account);

}
