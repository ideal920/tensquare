package com.tensquare.qa.rpc;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//标识该接口用于配置远程调用,并指定远程调用的微服务名称
// fallback 备用方案调用LabelClientImpl类
@FeignClient(value="tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    //表达远程调用的接口细节
    @RequestMapping(value="/label/{labelId}",method = RequestMethod.GET)
    Result findById(@PathVariable("labelId") String labelId);
}
