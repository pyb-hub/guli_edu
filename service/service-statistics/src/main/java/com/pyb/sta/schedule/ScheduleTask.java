package com.pyb.sta.schedule;

import com.pyb.sta.service.StatisticsDailyService;
import com.pyb.sta.utils.DateUtils;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/*统计模块的定时任务，每天1点自动生成前一天的统计数据*/

@Component
public class ScheduleTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    /**
     * 测试：每隔5秒执行一次这个方法
     */
    /*@Scheduled(cron = "0/5 * * * * ?")
    public void task(){
        System.out.println("task执行");
    }*/

    /**
     * 在每天凌晨一点，把前一天的数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void registerCountTask(){
        statisticsDailyService.setOneDaySta(DateUtils.formatDate(DateUtils.addDays(new Date(),-1)));
    }
}
