package com.jiwell.user.controller;

import com.jiwell.user.pojo.User;
import com.jiwell.user.service.UserService;
import com.jiwell.user.utils.RegisterState;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


/**
 * @Author: 98050
 * @Time: 2018-10-21 18:40
 * @Feature:
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用戶數據檢查
     * @param data
     * @param type
     * @return
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity<Boolean> checkUserData(@PathVariable("data") String data,@PathVariable(value = "type") Integer type){
        Boolean result = this.userService.checkData(data,type);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    /**
     * 發送短信驗證碼
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity senVerifyCode(@RequestParam("phone") String phone){
        Boolean result = this.userService.sendVerifyCode(phone);
        if (result == null || !result){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 發送短信碼
     * @param phone
     * @return
     */
    @PostMapping("register/code")
    public ResponseEntity sendPassword(@RequestParam("phone") String phone){
        Boolean result = this.userService.sendPassword(phone);
        if (result == null || !result){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 註冊
     * @param user
     * @param code
     * @return
             */
    @PostMapping("register")
    public ResponseEntity<Void> register(@Valid User user, @RequestParam("code") String code){
        Boolean result = this.userService.register(user,code);
        if(result == null || !result){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    /**
     * 用戶驗證
     * @param account
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("account")String account,@RequestParam("password")String password){
        User user = this.userService.queryUser(account,password);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("password")
    public ResponseEntity<Boolean> updatePassword(@RequestParam("account")String account,@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword){
        Boolean result = this.userService.updatePassword(account,oldPassword,newPassword);
        if (result == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("loginAndRegister")
    public ResponseEntity<RegisterState> loginAndRegister(@RequestParam("account")String account, @RequestParam("password")String password){
        RegisterState registerState = this.userService.loginAndRegister(account,password);
        if (registerState == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(registerState);
    }

    /**
     * 取得使用者資料
     * @param account
     * @return
     */
    @GetMapping("queryInfo")
    public ResponseEntity<User> queryUserByAccount(@RequestParam("account")String account){
        User user = this.userService.queryUserByAccount(account);
        if (user == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(user);
    }

}
