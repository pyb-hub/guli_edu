package com.pyb.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.order.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
public interface PayLogService extends IService<PayLog> {

    Map<String,Object> createWxNativePay(String orderNo);

    Map<String,String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}
