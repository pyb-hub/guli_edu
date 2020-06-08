package com.pyb.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Maps;
import com.pyb.order.entity.Order;
import com.pyb.order.entity.PayLog;
import com.pyb.order.mapper.PayLogMapper;
import com.pyb.order.service.OrderService;
import com.pyb.order.service.PayLogService;
import com.pyb.order.utils.HttpClient;
import com.pyb.servicebase.exceptionHandler.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {


    @Autowired
    private  OrderService orderService;

    /**
     * 根据订单号生成微信二维码
     *
     * @param orderNo 订单号
     * @return 二维码地址及其他信息
     */
    @Override
    public Map<String, Object> createWxNativePay(String orderNo) {

        //1.根据订单号查询订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(queryWrapper);


        //2.使用map 存储微信二维码需要的参数：key固定
        Map<String,String> map = Maps.newHashMap();

        /*参数：appid：微信支付需要的关联的公众号的id*/
        map.put("appid","wx74862e0dfcf69954");
        /*商户号*/
        map.put("mch_id", "1558950191");
        /*生成的随机字符串：使得生成的二维码唯一*/
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        //课程标题
        map.put("body", order.getCourseTitle());
        //二维码唯一标识：订单号
        map.put("out_trade_no", orderNo);
        /*二维码中订单的价格*/
        map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
        /*支付的ip地址：实际项目要改成域名：不用写接口*/
        map.put("spbill_create_ip", "127.0.0.1");
        /*支付后回调的地址：要修改*/
        map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        /*支付的类型：固定*/
        map.put("trade_type", "NATIVE");


        //3.发送httpclient请求，把上面的参数转化为xml格式传递，创建工具类来生成HttpClient对象

        /*地址参数为微信支付提供的固定的地址*/
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
        try {
            //设置xml格式的参数，导入的依赖中的WXPayUtil工具类把map集合转化为xml格式（key参数为提供的商户的key），由自定义的client工具类传递xml参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);

            //执行post请求发送
            client.post();

        //4.得到发送请求返回的内容
            //返回内容 是使用xml格式返回
            String xml = client.getContent();


            //把xml格式转换成map集合，把map集合返回：只有二维码信息，不够
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map dataMap = new HashMap();
            dataMap.put("out_trade_no", orderNo);
            dataMap.put("course_id", order.getCourseId());
            dataMap.put("total_fee", order.getTotalFee());
            //返回二维码操作状态码
            dataMap.put("result_code", resultMap.get("result_code"));
            //二维码地址
            dataMap.put("code_url", resultMap.get("code_url"));

            return dataMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(20001,"生成微信支付二维码出错");
        }
    }

    /**
     * 根据订单号查询订单支付状态
     *
     * @param orderNo 订单号
     * @return map 包括订单号，支付状态等
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        //1、封装参数：key不能改
        Map m = new HashMap<>();
        m.put("appid", "wx74862e0dfcf69954");
        m.put("mch_id", "1558950191");
        m.put("out_trade_no", orderNo);
        m.put("nonce_str", WXPayUtil.generateNonceStr());


        //2.发送httpClient参数为xml
        HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3.得到请求返回的内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            //4.转成map返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 支付成功，更新订单表支付状态，插入支付记录
     *
     * @param map
     */
    @Transactional
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        String orderNo = map.get("out_trade_no");

        //1.根据订单号查询订单信息
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(queryWrapper);

        //2.判断是否二次支付
        if (order.getStatus() == 1){
            return;
        }
        /*3.更新订单表支付状态*/
        order.setStatus(1);
        orderService.updateById(order);

        /*插入pay_log记录*/
        //向支付表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo)
                .setPayTime(new Date())
                .setPayType(1)
                .setTotalFee(order.getTotalFee())
                .setTradeState(map.get("trade_state"))
                .setTransactionId(map.get("transaction_id"))
                .setAttr(JSON.toJSONString(map));
        this.save(payLog);

    }

}
