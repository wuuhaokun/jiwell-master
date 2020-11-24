package com.jiwell.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: 98050
 * @Time: 2018-10-23 10:49
 * @Feature: 密码加密
 */
public class CodecUtils {

    public static String passwordBcryptEncode(String account,String password){

        return new BCryptPasswordEncoder().encode(account + password);
    }

    public static Boolean passwordConfirm(String rawPassword,String encodePassword){
        return new BCryptPasswordEncoder().matches(rawPassword,encodePassword);
    }
}
