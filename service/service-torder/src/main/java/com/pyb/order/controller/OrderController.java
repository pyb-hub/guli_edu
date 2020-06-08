package com.pyb.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.order.entity.Order;
import com.pyb.order.service.OrderService;
import com.pyb.result.Result;
import com.pyb.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-08
 */
@CrossOrigin
@RestController
@RequestMapping("/order")
@Api(tags = "订单接口")
public class OrderController {


    @Autowired
    private OrderService orderService;


    @ApiOperation("创建订单")
    @PostMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request) {
        /*判断是否登录*/
        if (!JwtUtils.checkToken(request)){
            return Result.error().message("请先登录");
        }
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,userId);

        return Result.ok().data("orderNo",orderNo);
    }

    @ApiOperation("根据订单id查询订单信息")
    @GetMapping("/getOrder/{orderNo}")
    public Result getOrder(@PathVariable String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);
        return Result.ok().data("order",order);
    }

    @ApiOperation("根据课程id和用户id查询订单表是否已经购买课程")/*修改前端页面课程详情显示的方法，要加个判断，需要远程调用这个方法*/
    @GetMapping("/checkOrder/{courseId}")
    public Result checkOrder(@PathVariable String courseId, HttpServletRequest request) {
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        Integer status = orderService.checkOrder(courseId,userId);
        return Result.ok().data("status",status);
    }

}

