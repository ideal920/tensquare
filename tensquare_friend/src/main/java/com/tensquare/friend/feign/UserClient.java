package com.tensquare.friend.feign;

import entity.Result;
import entity.StatusCode;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 维护关注数
     */
    @RequestMapping(value="/user/followcount/{id}/{count}",method= RequestMethod.PUT)
    public Result followcount(@PathVariable("id") String id, @PathVariable("count") Integer count);

    /**
     * 维护粉丝数
     */
    @RequestMapping(value="/user/fanscount/{id}/{count}",method= RequestMethod.PUT)
    public Result fanscount(@PathVariable("id") String id, @PathVariable("count") Integer count);
}
