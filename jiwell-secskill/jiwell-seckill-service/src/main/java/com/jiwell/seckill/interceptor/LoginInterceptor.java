package com.jiwell.seckill.interceptor;

import com.jiwell.auth.entity.UserInfo;
import com.jiwell.auth.utils.JwtUtils;

import com.jiwell.seckill.properties.JwtProperties;
import com.jiwell.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 98050
 * @Time: 2018-10-25 18:17
 * @Feature: 登錄攔截器
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {

    private JwtProperties jwtProperties;

    /**
     * 定義一個線程域，存放登錄用戶
     */
    private static final ThreadLocal<UserInfo> t1 = new ThreadLocal<>();

    public LoginInterceptor(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    /**
     *      * 在業務處理器處理請求之前被調用
     *      * 如果返回false
     *      * 則從當前的攔截器往回執行所有攔截器的afterCompletion(),再退出攔截器鏈
     *      * 如果返回true
     *      * 執行下一個攔截器，直到所有攔截器都執行完畢
     *      * 再執行被攔截的Controller
     *      * 然後進入攔截器鏈
     *      * 從最後一個攔截器往回執行所有的postHandle()
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.查詢token
        String token = CookieUtils.getCookieValue(request,jwtProperties.getCookieName());
        if(StringUtils.isBlank(token)) {
            token = JwtUtils.getJwtFromRequest(request);
            if (StringUtils.isBlank(token)){
                //2.未登錄，返回401
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }
        //3.有token，查詢用戶信息
        try{
            //4.解析成功，說明已經登錄
            UserInfo userInfo = JwtUtils.getInfoFromToken(token,jwtProperties.getPublicKey());
            //5.放入線程域
            t1.set(userInfo);
            return true;
        }catch (Exception e){
            //6.拋出異常，證明未登錄，返回401
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }
    }

    /**
     * 在業務處理器處理請求執行完成後，生成視圖之前執行的動作
     * 可在modelAndView中加入數據，比如當前時間
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 在DispatcherServlet完全處理完請求後被調用,可用於清理資源等
     * 當有攔截器拋出異常時,會從當前攔截器往回執行所有的攔截器的afterCompletion()
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
       t1.remove();
    }

    public static UserInfo getLoginUser(){
        return t1.get();
    }
}
