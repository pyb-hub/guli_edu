package com.pyb.sta.client;

import com.pyb.sta.client.impl.UcenterClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-ucenter-8006",fallback = UcenterClientImpl.class)
public interface UcenterClient {

    @GetMapping("/ucenter/member/getOneDayRegisterNum/{day}")
    public Integer getOneDayRegisterNum(@PathVariable("day")String day);
}
