package com.jiwell.user.service;

import com.jiwell.user.pojo.User;
import com.jiwell.user.utils.RegisterState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:41
 * @Feature:
 */
public interface UserService {
    /**
     * 检查用户名和手机号是否可用
     * @param data
     * @param type
     * @return
     */
    Boolean checkData(String data, Integer type);

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    Boolean sendVerifyCode(String phone);

    /**
     * 发送密碼從手机
     * @param phone
     * @return
     */
    Boolean sendPassword(String phone);

    /**
     * 用户注册
     * @param user
     * @param code
     * @return
     */
    Boolean register(User user, String code);

    /**
     * 用户验证
     * @param account
     * @param password
     * @return
     */
    User queryUser(String account, String password);

    /**
     * 根据用户名修改密码
     * @param account
     * @param newPassword
     * @return
     */
    boolean updatePassword(String account,String oldPassword,String newPassword);

    /**
     * 使用驗證碼登入並註冊
     * @param account
     * @param password
     * @return
     */

    RegisterState loginAndRegister(String account, String password);

    /**
     * 取得使用者資料
     * @param account
     * @return
     */
    User queryUserByAccount(String account);
}
