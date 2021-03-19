package com.jiwell.cart.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: 98050
 * @Time: 2018-10-25 20:00
 * @Feature:
 */
//@WebFilter(filterName = "CartFilter",urlPatterns = {"/**"})
public class CartFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("過濾器初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("具體過濾規則");
    }

    @Override
    public void destroy() {
        System.out.println("銷毀");
    }
}
