package com.pyb.edu.mapper;

import com.pyb.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pyb.edu.entity.vo.CourseConfirmVo;
import com.pyb.edu.entity.vo.CourseFrontDetailVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
public interface CourseMapper extends BaseMapper<Course> {
    public CourseConfirmVo getCourseConfirmInfo(@Param("id") String id);

    CourseFrontDetailVo getCourseFrontDetailInfo(@Param("id") String id);
}

