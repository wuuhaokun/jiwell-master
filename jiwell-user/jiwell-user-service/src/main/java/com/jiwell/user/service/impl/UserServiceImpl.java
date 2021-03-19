package com.jiwell.user.service.impl;

import com.jiwell.user.mapper.UserMapper;
import com.jiwell.user.pojo.User;
import com.jiwell.user.service.UserService;
import com.jiwell.user.utils.RegisterState;
import com.jiwell.utils.CodecUtils;
import com.jiwell.utils.JsonUtils;
import com.jiwell.utils.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 98050
 * @Time: 2018-10-21 18:42
 * @Feature:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String KEY_PREFIX = "user:code:phone";

    private static final String KEY_PREFIX2 = "jiwell:user:info";

    private static final String KEY_PASSWORD_PREFIX = "user:code:password";

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public Boolean checkData(String data, Integer type) {
        User user = new User();
        switch (type){
            case 1 :
                user.setAccount(data);
                break;
            case 2 :
                user.setPhone(data);
                break;
            default:
                return null;
        }
        return this.userMapper.selectCount(user) == 0;
    }

    /**
     * 發送短信驗證碼
     * @param phone
     */
    @Override
    public Boolean sendVerifyCode(String phone) {

        //1.生成验证码
        String code = NumberUtils.generateCode(6);

        try {
            Map<String,String> msg = new HashMap<>();
            msg.put("phone",phone);
            msg.put("code",code);

            //2.發送短信 //先改為MAIL
            //this.amqpTemplate.convertAndSend("jiwell.sms.exchange","sms.verify.code",msg);
            this.amqpTemplate.convertAndSend("jiwell.mail.exchange","mail.verify.code",msg);

            //3.將code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PREFIX + phone,code,5, TimeUnit.MINUTES);

            return true;

        }catch (Exception e){
            logger.error("發送短信失敗。phone：{}，code：{}",phone,code);
            return false;
        }
    }

    /**
     * 发送密碼從手机
     * @param phone
     * @return
     */
    @Override
    public Boolean sendPassword(String phone) {

        //1.生成验证码
        String code = NumberUtils.generateCode(6);

        try {
            Map<String,String> msg = new HashMap<>();
            msg.put("phone",phone);
            msg.put("password",code);

            //2.發送短信 //先改為MAIL
            //this.amqpTemplate.convertAndSend("jiwell.sms.exchange","sms.verify.code",msg);
            this.amqpTemplate.convertAndSend("jiwell.mail.exchange","mail.verify.code",msg);

            //3.將code存入redis
            this.stringRedisTemplate.opsForValue().set(KEY_PASSWORD_PREFIX + phone,code,5, TimeUnit.MINUTES);

            return true;

        }catch (Exception e){
            logger.error("發送短信失敗。phone：{}，code：{}",phone,code);
            return false;
        }
    }

    @Override
    public Boolean register(User user, String code) {
        String key = KEY_PREFIX + user.getPhone();
        //1.從redis中取出驗證碼
        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
        //2.檢查驗證碼是否正確
        if(codeCache == null || !codeCache.equals(code)){
            //不正確，返回
            return false;
        }
        user.setId(null);
        user.setCreated(new Date());
        //3.密碼加密
        String encodePassword = CodecUtils.passwordBcryptEncode(user.getAccount().trim(),user.getPassword().trim());
        user.setPassword(encodePassword);
        //4.寫入數據庫
        boolean result = this.userMapper.insertSelective(user) == 1;
        //5.如果註冊成功，則刪掉redis中的code
        if (result){
            try{
                this.stringRedisTemplate.delete(KEY_PREFIX + user.getPhone());
            }catch (Exception e){
                logger.error("刪除緩存驗證碼失敗，code:{}",code,e);
            }
        }
        return result;
    }

    /**
     * 用戶驗證
     * @param account
     * @param password
     * @return
     */
    @Override
    public User queryUser(String account, String password) {
        /**
         * 邏輯改變，先去緩存中查詢用戶數據，查到的話直接返回，查不到再去數據庫中查詢，然後放入到緩存當中
         */
        //1.緩存中查詢
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX2);
        String userStr = (String) hashOperations.get(account);
        User user;
        if (StringUtils.isEmpty(userStr)){
            //在緩存中沒有查到，去數據庫查,查到放入緩存當中
            User record = new User();
            record.setAccount(account);
            user = this.userMapper.selectOne(record);
            hashOperations.put(user.getAccount(), JsonUtils.serialize(user));
        } else {
            user = JsonUtils.parse(userStr,User.class);
        }
        //2.校驗用戶名
        if (user == null){
            return null;
        }
        //3. 校驗密碼
        boolean result = CodecUtils.passwordConfirm(account + password,user.getPassword());
        if (!result){
            return null;
        }

        //4.用戶名密碼都正確
        return user;
    }

    /**
     * 根據用戶名修改密碼
     * @param account
     * @param newPassword
     * @return
     */
    @Override
    public boolean updatePassword(String account,String oldPassword, String newPassword) {
        /**
            * 這裡面涉及到對緩存的操作：
            * 先把數據存到數據庫中，成功後，再讓緩存失效。
         */
        //1.讀取用戶信息
        User user = this.queryUser(account,oldPassword);
        if (user == null){
            return false;
        }
        //2.更新數據庫中的用戶信息 //這裡可以不用的，有寫錯
        //User updateUser = new User();
        //updateUser.setId(user.getId());

        //2.1密碼加密
        String encodePassword = CodecUtils.passwordBcryptEncode(account.trim(),newPassword.trim());
        user.setPassword(encodePassword);
        int result = this.userMapper.updateByPrimaryKeySelective(user);
        if(result == 0){
            return false;
        }
        //3.處理緩存中的信息
        //BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX+account);
        BoundHashOperations<String,Object,Object> hashOperations = this.stringRedisTemplate.boundHashOps(KEY_PREFIX2);

        hashOperations.delete(account);
        return true;
    }

    /**
     * 檢查account是存在。如是直接註冊，否則更新密碼
     * @param account
     * @param password
     * @return
     */
    @Override
    public RegisterState loginAndRegister(String account, String password){
        String key = KEY_PASSWORD_PREFIX + account;
        //1.從redis中取出驗證碼
        String codeCache = this.stringRedisTemplate.opsForValue().get(key);
        //2.檢查驗證碼是否正確
        if(codeCache == null || !codeCache.equals(password)){
            //不正確，返回
            return null;
        }

        User record = new User();
        record.setAccount(account);
        User user = this.userMapper.selectOne(record);
        if(user != null){
            return RegisterState.EXIST;
        }
        else{
            User newUser = new User();
            newUser.setAccount(account);
            newUser.setCreated(new Date());
            newUser.setPhone(account);
            //3.密碼加密
            String encodePassword = CodecUtils.passwordBcryptEncode(account.trim(),password.trim());
            newUser.setPassword(encodePassword);
            //4.寫入數據庫
            boolean result = this.userMapper.insertSelective(newUser) == 1;
            //5.如果註冊成功，則刪掉redis中的code
            if (result){
                try{
                    this.stringRedisTemplate.delete(KEY_PASSWORD_PREFIX + newUser.getPhone());
                }catch (Exception e){
                    logger.error("刪除緩存密碼失敗，code:{}",password,e);
                }
                return RegisterState.REGISTER;
            }
            else{
                return RegisterState.FAIL;
            }

        }
    }

    /**
     * 取得使用者資料
     * @param account
     * @return
     */
    @Override
    public User queryUserByAccount(String account) {
        User record = new User();
        record.setAccount(account);
        User user = this.userMapper.selectOne(record);
        return user;
    }

}
