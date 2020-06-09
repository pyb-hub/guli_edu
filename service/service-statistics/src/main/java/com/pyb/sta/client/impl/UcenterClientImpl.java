package com.pyb.sta.client.impl;


import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.sta.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {
    @Override
    public Integer getOneDayRegisterNum(String day) {
        throw new MyException(20001,"服务器异常，熔断器工作了");
    }
}
