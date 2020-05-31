package com.pyb.edu.service.impl;

import com.pyb.config.exceptionHandler.MyException;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.CourseDescription;
import com.pyb.edu.entity.vo.CourseInfo;
import com.pyb.edu.mapper.CourseMapper;
import com.pyb.edu.service.CourseDescriptionService;
import com.pyb.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        boolean b = this.updateById(course);
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfo,courseDescription);
        boolean c = descriptionService.updateById(courseDescription);
        if (!(b&&c)){
            throw new MyException(20002,"修改课程失败");
        }
        return course.getId();
    }
}
