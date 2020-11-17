package com.jiwell.fcm.service.impl;
import com.jiwell.fcm.mapper.JwFirebaseMapper;
import com.jiwell.fcm.pojo.JwFirebase;
import com.jiwell.fcm.service.JwFirebaseService;
import com.jiwell.utils.CodecUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:47
 * @Feature:
 */
@Service
public class JwFirebaseServiceImpl implements JwFirebaseService {

    @Autowired
    private JwFirebaseMapper jwFirebaseMapper;

    /**
     * Firebase token存入
     * @param jwFirebase
     * @return
     */

    @Override
    public boolean saveJwFirebase(JwFirebase jwFirebase){
        String token = queryTokenByUserId(jwFirebase.getUserId());
        //添加tokens
        if(token != null && !token.isEmpty()) {
            List<String> tokens = new ArrayList<>(Arrays.asList(token.split(",")));
            for(String firebaseToken:tokens){
                if(firebaseToken.equals(jwFirebase.getToken())){
                    return true;
                }
            }
            if(tokens.size() == 5){//同一個ID最多支援5個設備，超過5個從
                tokens.set(0,jwFirebase.getToken());
            }
            else{
                tokens.add(jwFirebase.getToken());
            }
            String tokenJoined = String.join(",", tokens);
            jwFirebase.setToken(tokenJoined);
            return this.jwFirebaseMapper.updateByPrimaryKeySelective(jwFirebase) == 1;
        }
        else{
            return this.jwFirebaseMapper.insertSelective(jwFirebase) == 1;
        }
    }

    @Override
    public String queryTokenByUserId(Long userId){
        JwFirebase jwFirebase = new JwFirebase();
        jwFirebase.setUserId(userId);
        jwFirebase = this.jwFirebaseMapper.selectOne(jwFirebase);
        if(jwFirebase == null){
            return null;
        }
        return  jwFirebase.getToken();
        //return this.queryToken(userId);
    }

//    private String queryToken(Long userId){
//        JwFirebase jwFirebase = new JwFirebase();
//        jwFirebase.setUserId(userId);
//        jwFirebase = this.jwFirebaseMapper.selectOne(jwFirebase);
//        if(jwFirebase == null){
//            return "";
//        }
//        return  jwFirebase.getToken();
//    }
}
