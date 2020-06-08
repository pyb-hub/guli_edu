package com.pyb.order.controller;


import com.pyb.order.service.PayLogService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
@RestController
@RequestMapping("/order/pay")
@CrossOrigin
@Api(tags = "微信支付接口")
public class PayLogController {

    private final PayLogService payLogService;

    public PayLogController(PayLogService payLogService) {
        this.payLogService = payLogService;
    }
    /*@Autowired
    private PayLogService payLogService;*/

    @ApiOperation("生成支付微信二维码")
    @GetMapping("/createNative/{orderNo}")
    public Result createWxNativePay(@PathVariable String orderNo){
        //返回的信息包括二维码地址，还需要其他的信息
        Map<String,Object> map = payLogService.createWxNativePay(orderNo);
        return Result.ok().data(map);
    }

    @ApiOperation("扫码后，查询订单的支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){

        /*返回信息*/
        Map<String,String> map =  payLogService.queryPayStatus(orderNo);

        if(CollectionUtils.isEmpty(map)){
            return Result.error().message("支付出错了");
        }

        //如果返回的map不为空：通过map获取订单内容
        if("SUCCESS".equals(map.get("trade_state"))){
            //支付成功：添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return Result.ok().message("支付成功");
        }
        /*还在支付：如果写error，前端会返回支付错误，所以写ok，前端看支付状态码是不是success来判断*/
        return Result.ok().message("支付中");
    }



}

