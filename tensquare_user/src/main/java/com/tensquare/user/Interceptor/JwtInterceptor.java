package com.tensquare.user.Interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//鉴权拦截器
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //校验权限
        String token = request.getHeader("Authorization");
        //获得请求头 ||是否以Bearer开头
        if(StringUtils.isEmpty(token) || !token.startsWith("Bearer ") ){
            return true;//放行
        }
        //提取token
        token = token.substring(7);
        //jwt工具类解析token
        Claims claims = null;
        try {
            claims = jwtUtils.parseJwt(token);
        } catch (Exception e) {
           return true;//解析token失败=>放行
        }

        //将提取的token的载荷放入request域
        if(claims.get("roles",String.class).equals("admin")){
            request.setAttribute("admin_claims", claims);
        }else{
            request.setAttribute("user_claims", claims);
        }

        return true;
    }
}
