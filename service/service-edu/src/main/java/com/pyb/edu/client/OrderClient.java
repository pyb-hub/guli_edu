package com.pyb.edu.client;

import com.pyb.edu.client.impl.OrderClientImpl;
import com.pyb.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Component
@FeignClient(name = "service-torder-8007",fallback = OrderClientImpl.class)
public interface OrderClient {

    @GetMapping("/order/checkOrder/{courseId}")
    public Result checkOrder(@PathVariable("courseId") String courseId, HttpServletRequest request);
}
