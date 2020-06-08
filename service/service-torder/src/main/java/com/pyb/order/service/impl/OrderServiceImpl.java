package com.pyb.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyb.order.client.CourseClient;
import com.pyb.order.client.UcenterClient;
import com.pyb.order.entity.Order;
import com.pyb.order.mapper.OrderMapper;
import com.pyb.order.service.OrderService;
import com.pyb.order.utils.OrderNoUtils;
import com.pyb.vo.OrderCourseVo;
import com.pyb.vo.OrderUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseClient courseClient;
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public String createOrder(String courseId, String userId) {
        //查询课程信息
        OrderCourseVo courseInfo = courseClient.findCourseInfo(courseId);
        //查询用户信息
        OrderUserVo userInfo = ucenterClient.getUserInfo(userId);

        Order order = new Order();
        /*订单信息：编号：uuid生成唯一*/
        order.setOrderNo(OrderNoUtils.OrderNo());
        /*状态:未支付，微信支付*/
        order.setStatus(0);
        order.setPayType(1);
        /*课程信息*/
        order.setCourseId(courseInfo.getId());
        order.setCourseTitle(courseInfo.getTitle());
        order.setCourseCover(courseInfo.getCover());
        order.setTeacherName(courseInfo.getTeacherName());
        order.setTotalFee(courseInfo.getPrice());
        /*购买的人信息*/
        order.setMemberId(userInfo.getId());
        order.setNickname(userInfo.getNickname());
        order.setMobile(userInfo.getMobile());

        this.save(order);
        return order.getOrderNo();
    }

    @Override
    public Integer checkOrder(String courseId, String userId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",userId);
        wrapper.eq("status",1);
        int count = this.count(wrapper);
        return count;
    }
}
