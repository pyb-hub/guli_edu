package com.pyb.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.sta.client.UcenterClient;
import com.pyb.sta.entity.StatisticsDaily;
import com.pyb.sta.mapper.StatisticsDailyMapper;
import com.pyb.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-08
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    @Override
    @Transactional
    public StatisticsDaily setOneDaySta(String day) {
        /*先获得信息*/
        /*每天的统计人数*/
        Integer oneDayRegisterNum = ucenterClient.getOneDayRegisterNum(day);

        /*插入到统计信息表，要覆盖插入*/
        /*删除原来的重复数据*/
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        this.remove(wrapper);

        /*插入新数据*/
        StatisticsDaily daily = new StatisticsDaily();
        daily.setRegisterNum(oneDayRegisterNum);
        daily.setDateCalculated(day);
        /*省略处理其他的信息*/
        daily.setLoginNum(1);
        daily.setVideoViewNum(1);
        daily.setCourseNum(1);


        this.save(daily);
        return daily;
    }

    @Override
    public HashMap<String, List> getOneDaySta(String type, String begin, String end) {
        /*默认参数不为空：前端做表单校验*/
        if (StringUtils.isEmpty(type)){
            return null;
        }
        /*查询得到数据*/
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        /*查询返回的数据，二个：天数日期和该type在该时间内对应的数量*/
        wrapper.select("date_calculated",type);

        List<StatisticsDaily> data = this.list(wrapper);

        /*把统计日期和type对应的数量分别封装成二个list，前端需要数组形式的数据*/
        ArrayList<String> day = new ArrayList<>();
        ArrayList<Integer> numb = new ArrayList<>();

        for (StatisticsDaily s:data) {
            /*日期封装*/
            day.add(s.getDateCalculated());

            /*数量封装：因为对应不同type的数量，所有要讨论统计哪种类型*/
            switch(type){
                case "register_num":
                    numb.add(s.getRegisterNum());
                    break;
                case "login_num":
                    numb.add(s.getLoginNum());
                    break;
                case "video_view_num":
                    numb.add(s.getVideoViewNum());
                    break;
                case "course_num":
                    numb.add(s.getCourseNum());
                    break;
            }

        }

        /*用map封装*/
        HashMap<String,List> map = new HashMap<>();
        map.put("day",day);
        map.put("numb",numb);
        return map;

    }
}
