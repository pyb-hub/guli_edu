package com.pyb.msm.controller;

import com.pyb.msm.service.MsmService;
import com.pyb.msm.utils.MsgCodeUtils;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
//@CrossOrigin 和gateway里面处理二选一，不能处理二次
@Api(tags = "短信发送接口")
@RequestMapping("/msm/message")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("sendMsg/{phone}")
    @ApiOperation("短信发送功能")
    public Result sendMsg(@PathVariable String phone) {
        /*生成四位随机数验证码*/
        String code = MsgCodeUtils.getFourBitRandom();
        /*在service进行aliyun上传短信服务*/
        boolean result = msmService.sendMsg(phone,code);
        if (result){
            /*设置验证码5分内有效*/
            redisTemplate.opsForValue().set(phone+"MsgCode",code,5,TimeUnit.MINUTES);
            return Result.ok();
        }

        return Result.error();
    }

}
