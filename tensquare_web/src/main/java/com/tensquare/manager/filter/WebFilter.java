package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

//网关过滤器
@Component
public class WebFilter extends ZuulFilter {
    @Override
    /*
        配置方法 => 配置网关过滤器类型
        pre ：可以在请求被路由之前调用
        route ：在路由请求时候被调用
        post ：在route和error过滤器之后被调用
        error ：处理请求时发生错误时被调用
     */
    public String filterType() {

        return "pre";
    }

    @Override
    /*
    配置过滤器的执行权重(优先级)
    0 => 最高优先级
     */
    public int filterOrder() {
        return 0;
    }

    @Override
    /*
    开关配置
        true:启用当前过滤器
        false:禁用当前过滤器
     */
    public boolean shouldFilter() {
        return true;
    }

    @Override
    //过滤器方法,每次经过网关都会执行该方法
    public Object run() throws ZuulException {
        System.out.println("经过过滤器了!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
/*
//向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获得request对象
        HttpServletRequest request = requestContext.getRequest();
        //从request中获得Authorization请求头(JWT)
        String authorization = request.getHeader("Authorization");
        if(authorization!=null){
            //在转发的请求中,也加入Authorization头
            requestContext.addZuulRequestHeader("Authorization",authorization);
        }
*/

        return null;//放行
    }
}
