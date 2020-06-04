package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.servicebase.exceptionHandler.MyException;
import com.pyb.edu.client.VodClient;
import com.pyb.edu.entity.*;
import com.pyb.edu.entity.vo.CourseConfirmVo;
import com.pyb.edu.entity.vo.CourseInfo;
import com.pyb.edu.entity.vo.CourseVo;
import com.pyb.edu.mapper.CourseMapper;
import com.pyb.edu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService descriptionService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @Override
    @Transactional
    public String saveCourseInfo(CourseInfo courseInfo) {
        /*添加课程*/
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        boolean save = this.save(course);
        if (!save){
            throw new MyException(20001,"课程添加有误");
        }
        /*添加课程描述,因为是一对一关系，所以设置他们的主键id值相同*/
        String id = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfo,courseDescription);
        courseDescription.setId(id);
        boolean save1 = descriptionService.save(courseDescription);
        if (!save1){
            throw new MyException(20001,"描述添加有误");
        }


        return id;
    }

    @Override
    public CourseInfo getCourseInfoById(String id) {
        Course byId = this.getById(id);
        CourseDescription description = descriptionService.getById(id);

        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(byId,courseInfo);
        BeanUtils.copyProperties(description,courseInfo);
        return courseInfo;
    }

    @Override
    public String updateCourseInfo(CourseInfo courseInfo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo,course);
        Long version = courseInfo.getVersion();
        /*乐观锁版本号设置*/
        course.setVersion(version+1);
        boolean b = this.updateById(course);
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfo,courseDescription);
        boolean c = descriptionService.updateById(courseDescription);
        if (!(b&&c)){
            throw new MyException(20002,"修改课程失败");
        }
        return course.getId();
    }

    @Override
    public CourseConfirmVo getCourseConfirmInfoById(String id) {
        return baseMapper.getCourseConfirmInfo(id);
    }

    @Override
    public Page<Course> courseList(int page, int size, CourseVo courseVo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        Page<Course> coursePage = new Page<>(page,size);

        String title = courseVo.getTitle();
        String status = courseVo.getStatus();
        String publishTime = courseVo.getPublishTime();
        String updateTime = courseVo.getUpdateTime();

        if (!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if (!StringUtils.isEmpty(status)){
            wrapper.eq("status",status);
        }
        if (!StringUtils.isEmpty(publishTime)){
            wrapper.ge("gmt_create",publishTime);
        }
        if (!StringUtils.isEmpty(updateTime)){
            wrapper.le("gmt_modified",updateTime);
        }
        /*根据修改时间排序*/
        wrapper.orderByDesc("gmt_modified");
        /*分页条件查询*/
        baseMapper.selectPage(coursePage, wrapper);
        return coursePage;
    }

    @Override
    @Transactional
    public Boolean delCourse(String id) {
        /*删除课程*/
        int i = baseMapper.deleteById(id);
        /*删除描述*/
        descriptionService.removeById(id);
        /*删除章节*/
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        int count = chapterService.count(wrapper);
        /*判断非空，少一次数据库操作*/
        if (count != 0){
            chapterService.remove(wrapper);
        }
        /*删除视频*/
        QueryWrapper<Video> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",id);
        /*查询返回的字段*/
        wrapper2.select("video_source_id");
        /*查询video对应的sourceId*/
        List<Video> videoList = videoService.list(wrapper2);
        if (videoList.size()>0){
            for (Video video : videoList) {
                /*删除阿里云的视频*/
                String videoSourceId = video.getVideoSourceId();
                if (!StringUtils.isEmpty(videoSourceId)){
                    vodClient.removeVideo(videoSourceId);
                }
            }
            /*删除video对应的小节*/
            videoService.remove(wrapper2);
        }
        return i!=0;
    }

    @Override
    @Cacheable(cacheNames = "courses",key = "'frontIndex'")
    public List<Course> courseIndex() {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");
        return this.list(wrapper);
    }
}
