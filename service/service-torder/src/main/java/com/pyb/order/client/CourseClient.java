package com.pyb.order.client;

import com.pyb.order.client.impl.CourseClientImpl;
import com.pyb.vo.OrderCourseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-edu-8001",fallback = CourseClientImpl.class)
public interface CourseClient {

    /*根据id查询课程信息*/
    @GetMapping("/edu/front/course/findCourseInfo/{courseId}")
    public OrderCourseVo findCourseInfo(@PathVariable("courseId") String courseId);
}
