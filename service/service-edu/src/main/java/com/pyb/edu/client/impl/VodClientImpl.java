package com.pyb.edu.client.impl;

import com.pyb.edu.client.VodClient;
import com.pyb.result.Result;
import org.springframework.stereotype.Component;

/*熔断器，熔断之后执行的方法*/
@Component
public class VodClientImpl implements VodClient {
    @Override
    public Result removeVideo(String sourceId) {
        return Result.error().message("删除视频出错~熔断器执行");
    }
}
