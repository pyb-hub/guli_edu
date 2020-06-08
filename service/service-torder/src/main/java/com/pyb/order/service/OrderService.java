package com.pyb.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.order.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String userId);

    Integer checkOrder(String courseId, String userId);
}
