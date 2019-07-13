package com.tensquare.qa.rpc;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

//远程调用的"备用方案"
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String labelId) {
        return new Result(true, StatusCode.OK,"启用了备用方案");
    }
}
