package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.config.exceptionHandler.MyException;
import com.pyb.edu.entity.Chapter;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.CourseDescription;
import com.pyb.edu.entity.Video;
import com.pyb.edu.entity.vo.CourseConfirmVo;
import com.pyb.edu.entity.vo.CourseInfo;
import com.pyb.edu.entity.vo.CourseVo;
import com.pyb.edu.mapper.CourseMapper;
import com.pyb.edu.service.ChapterService;
import com.pyb.edu.service.CourseDescriptionService;
import com.pyb.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyb.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        boolean b = descriptionService.removeById(id);
        /*删除章节*/
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        boolean remove = chapterService.remove(wrapper);
        /*删除小节*/
        QueryWrapper<Video> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_id",id);
        boolean remove1 = videoService.remove(wrapper2);
        /*TODO 删除视频地址*/

        return i!=0 && b && remove && remove1;
    }
}
