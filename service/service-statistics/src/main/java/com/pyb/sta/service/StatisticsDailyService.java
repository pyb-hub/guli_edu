package com.pyb.sta.service;

import com.pyb.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-08
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    StatisticsDaily setOneDaySta(String day);

    HashMap<String,List> getOneDaySta(String type, String begin, String end);
}
