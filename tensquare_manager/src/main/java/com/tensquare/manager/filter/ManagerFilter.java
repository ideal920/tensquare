package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import util.JwtUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private JwtUtils jwtUtils;

    @Override
        //校验管理员权限
    public Object run() throws ZuulException {
        RequestContext rc = RequestContext.getCurrentContext();
        HttpServletRequest request = rc.getRequest();
        if(request.getMethod().equals("OPTIONS")){
        //判断请求方式如果为options,直接放行
            return null;
        }
        if(request.getRequestURI().indexOf("/login")>0){
        //判断请求路径是否为登录路径, 放行登录请求
            return null;
        }
        //获得Authorization头,提取token
        String token = request.getHeader("Authorization");
        if(!StringUtils.isEmpty(token) && token.startsWith("Bearer ")){
        //解析token
            token = token.substring(7);
            Claims claims = jwtUtils.parseJwt(token);
            //判断roles是否为admin
            if("admin".equals(claims.get("roles"))){
                return null;//放行
            }
        }
            //校验失败=>不转发请求,并提示没有权限
        rc.setResponseStatusCode(500);//指定返回响应状态码
        rc.setResponseBody("<font color='red' >您无权访问!</font>");//指定返回响应正文
        rc.addZuulResponseHeader("Content-Type", "text/html;charset=utf-8"); //配置content-type头
        rc.setSendZuulResponse(false); //配置不转发请求
        return null;
    }
}
