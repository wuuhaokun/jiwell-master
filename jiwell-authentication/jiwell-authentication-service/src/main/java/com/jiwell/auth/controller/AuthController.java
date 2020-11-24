package com.jiwell.auth.controller;

import com.jiwell.auth.entity.RegisterInfo;
import com.jiwell.auth.entity.TokenInfo;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.auth.properties.JwtProperties;
import com.jiwell.auth.service.AuthService;
import com.jiwell.auth.utils.JwtUtils;
import com.jiwell.user.pojo.User;
import com.jiwell.utils.CodecUtils;
import com.jiwell.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.PrivateKey;
import java.util.Date;

/**
 * @Author: 98050
 * @Time: 2018-10-23 22:43
 * @Feature: 登录授权
 */
@Controller
//@EnableConfigurationProperties(JwtProperties.class)
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtProperties properties;

    /**
     * 登录授权
     * @param account
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<TokenInfo> authentication(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
//        //1.登录校验
//        String token = this.authService.authentication(account,password);
//        if (StringUtils.isBlank(token)){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        //2.将token写入cookie，并指定httpOnly为true，防止通过js获取和修改
//        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);
//        TokenInfo tokenInfo = new TokenInfo(token);
//        return ResponseEntity.ok(tokenInfo);
        String token = this.authService.authentication(account,password);
        TokenInfo tokenInfo = cretaeTokenInfo(request,response,token);
        if(tokenInfo == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(tokenInfo);
    }

    /**
     * 登录授权並註冊
     * @param account
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit/code")
    public ResponseEntity<RegisterInfo> authenticationAndRegister(
            @RequestParam("account") String account,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        //1.登录校验
        RegisterInfo registerInfo = this.authService.authenticationAndRegister(account,password);
        //String token = this.authService.authenticationAndRegister(account,password);
        if(registerInfo == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        TokenInfo tokenInfo = cretaeTokenInfo(request,response,registerInfo.getToken());

        return ResponseEntity.ok(registerInfo);
    }

    /**
     * 用户验证
     * @param token
     * @return GWell
     */
    //
    @GetMapping("verify")
    public ResponseEntity<UserInfo> verifyUser(@CookieValue("LY_TOKEN") String token,HttpServletRequest request,
                                               HttpServletResponse response){
        try{
            String userToken = token;
            String headerToken = JwtUtils.getJwtFromRequest(request);
            if (StringUtils.isBlank(userToken) || userToken == null){
                //有header 傳入的token
                if(StringUtils.isNotBlank(headerToken)){
                    userToken = headerToken;
                }
                else{
                    //2.未登录，返回401
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
            }
            return ResponseEntity.ok(this.parseUserFromToken(request, response, userToken));
        }catch (Exception e){
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("verify1")
    public ResponseEntity<UserInfo> verifyUser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String headerToken = JwtUtils.getJwtFromRequest(request);
            if (StringUtils.isBlank(headerToken) || headerToken == null) {
                //有header 傳入的token
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            }
            return ResponseEntity.ok(this.parseUserFromToken(request, response, headerToken));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private UserInfo parseUserFromToken(HttpServletRequest request, HttpServletResponse response,String headerToken){
        try {
        //1.从token中解析token信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(headerToken, this.properties.getPublicKey()); //.getInfoFromToken(headerToken, this.properties.getPublicKey());
        //2.解析成功要重新刷新token
        headerToken = JwtUtils.generateToken(userInfo, this.properties.getPrivateKey(), this.properties.getExpire());
        //3.更新Cookie中的token
        CookieUtils.setCookie(request, response, this.properties.getCookieName(), headerToken, this.properties.getCookieMaxAge());
        //4.解析成功返回用户信息
            return userInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private TokenInfo cretaeTokenInfo(HttpServletRequest request, HttpServletResponse response, String token){
        if (StringUtils.isBlank(token)){
            return null;
        }
        //2.将token写入cookie，并指定httpOnly为true，防止通过js获取和修改
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);
        //TokenInfo tokenInfo = new TokenInfo(token);
        return new TokenInfo(token);
    }
}
