package com.pyb.order.client.impl;

import com.pyb.order.client.CourseClient;
import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.vo.OrderCourseVo;
import org.springframework.stereotype.Component;

@Component
public class CourseClientImpl implements CourseClient {

    @Override
    public OrderCourseVo findCourseInfo(String courseId) {
        throw new MyException(20001,"服务器出故障,正在维护");
    }
}
