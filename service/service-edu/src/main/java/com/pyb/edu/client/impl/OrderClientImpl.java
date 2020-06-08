package com.pyb.edu.client.impl;

import com.pyb.edu.client.OrderClient;
import com.pyb.result.Result;
import com.pyb.servicebase.exceptionHandler.MyException;

import javax.servlet.http.HttpServletRequest;

public class OrderClientImpl implements OrderClient {
    @Override
    public Result checkOrder(String courseId, HttpServletRequest request) {
        throw new MyException(20001,"服务器异常，熔断器工作了");
    }
}
