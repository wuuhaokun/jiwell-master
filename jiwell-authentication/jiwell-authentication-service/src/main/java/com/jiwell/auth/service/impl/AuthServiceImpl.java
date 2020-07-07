package com.jiwell.auth.service.impl;

import com.jiwell.auth.client.UserClient;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.auth.properties.JwtProperties;
import com.jiwell.auth.service.AuthService;
import com.jiwell.auth.utils.JwtUtils;
import com.jiwell.user.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    /**
     * 用户授权
     * @param username
     * @param password
     * @return
     */
    @Override
    public String authentication(String username, String password) {

        try{
            //1.调用微服务查询用户信息
            User user = this.userClient.queryUser(username,password);
            //2.查询结果为空，则直接返回null
            if (user == null){
                return null;
            }
            PrivateKey privateKey = properties.getPrivateKey();
            int expire = properties.getExpire();
            UserInfo userInfo = new UserInfo(user.getId(), user.getUsername(),user.getPhone(),user.getGender(),user.getNickname());
            //3.查询结果不为空，则生成token
            String token = JwtUtils.generateToken(userInfo,
                    privateKey, expire);
            return token;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
