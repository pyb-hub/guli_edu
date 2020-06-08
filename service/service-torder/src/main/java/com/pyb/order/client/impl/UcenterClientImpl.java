package com.pyb.order.client.impl;

import com.pyb.order.client.UcenterClient;
import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.vo.OrderUserVo;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientImpl implements UcenterClient {

    /*根据id查询用户信息*/
    @Override
    public OrderUserVo getUserInfo(String userId) {

        throw new MyException(20001,"服务器出故障,正在维护");
    }


}
