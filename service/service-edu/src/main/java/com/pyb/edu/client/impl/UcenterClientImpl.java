package com.pyb.edu.client.impl;

import com.pyb.edu.client.UcenterClient;
import com.pyb.result.Result;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/*熔断器，熔断之后执行的方法*/
@Component
public class UcenterClientImpl implements UcenterClient {

    @Override
    public Result getCommentUserByToken(HttpServletRequest request) {
        return Result.error().message("获取用户信息失败~熔断器执行");
    }
}
