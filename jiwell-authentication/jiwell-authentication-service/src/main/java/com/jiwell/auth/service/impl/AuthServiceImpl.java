package com.jiwell.auth.service.impl;

import com.jiwell.auth.client.UserClient;
import com.jiwell.auth.entity.RegisterInfo;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.auth.properties.JwtProperties;
import com.jiwell.auth.service.AuthService;
import com.jiwell.auth.utils.JwtUtils;
import com.jiwell.user.pojo.User;
import com.jiwell.user.utils.RegisterState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.PrivateKey;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final String KEY_PASSWORD_PREFIX = "user:code:password";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtProperties properties;

    /**
     * 用户授权
     * @param account
     * @param password
     * @return
     */
    @Override
    public String authentication(String account, String password) {

        try{
            //1.调用微服务查询用户信息
            User user = this.userClient.queryUser(account,password);
            //2.查询结果为空，则直接返回null
            if (user == null){
                return null;
            }
            return this.createToken(user);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RegisterInfo authenticationAndRegister(String account, String password) {

//        String key = KEY_PASSWORD_PREFIX + account;
//        //1.从redis中取出验证码
//        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
//        //2.检查验证码是否正确
//        if(codeCache == null || !codeCache.equals(password)){
//            //不正确，返回
//            return null;
//        }
        RegisterInfo registerInfo = new RegisterInfo();
        RegisterState registerState = userClient.loginAndRegister(account,password);
        if(registerState == null){
            return null;
        }
        if(registerState == RegisterState.REGISTER || registerState == RegisterState.EXIST){
            try{
                User user = this.userClient.queryUserByAccount(account);
                registerInfo.setRegisterState(registerState);
                registerInfo.setToken(this.createToken(user));
                return registerInfo;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        else{
            return null;
        }
    }

    private String createToken(User user) throws Exception {
        PrivateKey privateKey = properties.getPrivateKey();
        int expire = properties.getExpire();
        UserInfo userInfo = new UserInfo(user.getId(), user.getAccount(),user.getPhone(),user.getGender(),user.getNickname());
        //3.查询结果不为空，则生成token
        String token = JwtUtils.generateToken(userInfo,
                privateKey, expire);
        return token;
    }
}
