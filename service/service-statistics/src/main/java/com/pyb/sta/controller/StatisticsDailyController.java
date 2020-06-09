package com.pyb.sta.controller;


import com.pyb.result.Result;
import com.pyb.sta.entity.StatisticsDaily;
import com.pyb.sta.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/sta/statistics")
@CrossOrigin
@Api(tags = "后台统计信息接口")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @ApiOperation("保存前一天的统计信息到表中")
    @GetMapping("/setOneDaySta/{day}")
    public Result setOneDaySta(@PathVariable("day")String day){
        StatisticsDaily daily = statisticsDailyService.setOneDaySta(day);
        return Result.ok().data("OneDaySta",daily);
    }

    @ApiOperation("得到统计信息")
    @GetMapping("/getOneDaySta/{type}/{begin}/{end}")
    public Result getOneDaySta(@PathVariable("type")String type,
                               @PathVariable("begin")String begin,
                               @PathVariable("end")String end){

        HashMap<String,List> staData= statisticsDailyService.getOneDaySta(type,begin,end);
        return Result.ok().data("staData",staData);
    }

}

