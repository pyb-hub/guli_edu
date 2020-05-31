package com.pyb.edu.service;

import com.pyb.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.edu.entity.vo.CourseInfo;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseInfoById(String id);

    String updateCourseInfo(CourseInfo courseInfo);
}
