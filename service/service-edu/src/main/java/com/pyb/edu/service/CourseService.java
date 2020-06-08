package com.pyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.edu.entity.vo.*;
import com.pyb.vo.OrderCourseVo;

import java.util.HashMap;
import java.util.List;

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

    CourseConfirmVo getCourseConfirmInfoById(String id);

    Page<Course> courseList(int page, int size, CourseVo courseVo);

    Boolean delCourse(String id);

    List<Course> courseIndex();

    HashMap<String,Object> getPageInfo(Integer currentPage, Integer size, CourseFrontVo courseFrontVo);

    CourseFrontDetailVo getCourseFrontDetailInfoById(String id);

    OrderCourseVo getOrderCourseById(String courseId);
}
