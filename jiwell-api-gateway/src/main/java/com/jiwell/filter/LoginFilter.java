package com.jiwell.filter;

import com.jiwell.auth.utils.JwtUtils;
import com.jiwell.config.FilterProperties;
import com.jiwell.config.JwtProperties;
import com.jiwell.utils.CookieUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: 98050
 * @Time: 2018-10-24 16:21
 * @Feature: 登錄攔截器
 */
@Component
//@EnableConfigurationProperties({JwtProperties.class,FilterProperties.class})
public class LoginFilter extends ZuulFilter {

    @Autowired
    private JwtProperties properties;

    @Autowired
    private FilterProperties filterProperties;

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 5;
    }

    @Override
    public boolean shouldFilter() {
        //1.獲取上下文
        RequestContext context = RequestContext.getCurrentContext();
        //2.獲取request
        HttpServletRequest request = context.getRequest();
        //3.獲取路徑
        String requestUri = request.getRequestURI();
        logger.info(requestUri);
        //4.判斷白名單
        return !isAllowPath(requestUri);
    }

    private boolean isAllowPath(String requestUri) {
        //1.定義一個標記
        boolean flag = false;

        //2.遍歷允許訪問的路徑
        List<String> paths = Arrays.asList(this.filterProperties.getAllowPaths().split(" "));
        for (String path : paths){
            if (requestUri.startsWith(path)){
                flag = true;
                break;
            }
        }
        return flag;
    }

// private static boolean isBlank(final CharSequence cs) {
// int strLen;
// if (cs == null || (strLen = cs.length()) == 0) {
// return true;
// }
// for (int i = 0; i < strLen; i++) {
// if (!Character.isWhitespace(cs.charAt(i))) {
// return false;
// }
// }
// return true;
// }

// private static boolean isNotBlank(final CharSequence cs) {
// return !isBlank(cs);
// }

    @Override
    public Object run() throws ZuulException {
        //1.獲取上下文
        RequestContext context = RequestContext.getCurrentContext();
        //2.獲取request
        HttpServletRequest request = context.getRequest();
        //3.獲取token
        String token = CookieUtils.getCookieValue(request,this.properties.getCookieName());
        //4.校驗
        try{
            //4.1 校驗通過，放行
            if(token == null || token.isEmpty()){
                token = JwtUtils.getJwtFromRequest(request);
            }
            JwtUtils.getInfoFromToken(token,this.properties.getPublicKey());
        }catch (Exception e){
            //4.2 校驗不通過，返回403
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }
        return null;
    }
}
