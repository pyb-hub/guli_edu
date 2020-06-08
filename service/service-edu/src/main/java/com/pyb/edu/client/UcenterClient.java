package com.pyb.edu.client;

import com.pyb.edu.client.impl.UcenterClientImpl;
import com.pyb.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/*要调用的客户端,fallback:服务熔断之后调用的方法*/
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
@Component
public interface UcenterClient {

    /*调用的方法:PathVariable一定要加注解，否则报错*/

    /*根据request中的token，获取用户信息，存入到cookie中*/
    @GetMapping("/ucenter/member/getCommentUserByToken")
    public Result getCommentUserByToken(HttpServletRequest request);

}
