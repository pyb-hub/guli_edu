package com.pyb.order.client;

import com.pyb.order.client.impl.UcenterClientImpl;
import com.pyb.vo.OrderUserVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    /*根据id查询用户信息*/
    @GetMapping("/ucenter/member/getUserInfo/{userId}")
    public OrderUserVo getUserInfo(@PathVariable("userId") String userId);
}
