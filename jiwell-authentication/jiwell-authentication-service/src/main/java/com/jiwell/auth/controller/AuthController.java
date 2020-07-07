package com.jiwell.auth.controller;

import com.jiwell.auth.entity.TokenInfo;
import com.jiwell.auth.entity.UserInfo;
import com.jiwell.auth.properties.JwtProperties;
import com.jiwell.auth.service.AuthService;
import com.jiwell.auth.utils.JwtUtils;
import com.jiwell.user.pojo.User;
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
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<TokenInfo> authentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpServletRequest request,
            HttpServletResponse response
    ){
        //1.登录校验
        String token = this.authService.authentication(username,password);
        if (StringUtils.isBlank(token)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        //2.将token写入cookie，并指定httpOnly为true，防止通过js获取和修改
        CookieUtils.setCookie(request,response,properties.getCookieName(),token,properties.getCookieMaxAge(),true);
        TokenInfo tokenInfo = new TokenInfo(token);
        return ResponseEntity.ok(tokenInfo);
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

            //1.从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(userToken,this.properties.getPublicKey());
            //2.解析成功要重新刷新token
            token = JwtUtils.generateToken(userInfo,this.properties.getPrivateKey(),this.properties.getExpire());
            //3.更新Cookie中的token
            CookieUtils.setCookie(request,response,this.properties.getCookieName(),userToken,this.properties.getCookieMaxAge());
            //4.解析成功返回用户信息
            return ResponseEntity.ok(userInfo);
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
            //1.从token中解析token信息
            UserInfo userInfo = JwtUtils.getInfoFromToken(headerToken, this.properties.getPublicKey());
            //2.解析成功要重新刷新token
            headerToken = JwtUtils.generateToken(userInfo, this.properties.getPrivateKey(), this.properties.getExpire());
            //3.更新Cookie中的token
            CookieUtils.setCookie(request, response, this.properties.getCookieName(), headerToken, this.properties.getCookieMaxAge());
            //4.解析成功返回用户信息
            return ResponseEntity.ok(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //5.出现异常,相应401
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}
