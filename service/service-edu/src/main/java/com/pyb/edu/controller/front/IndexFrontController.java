package com.pyb.edu.controller.front;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.Teacher;
import com.pyb.edu.service.CourseService;
import com.pyb.edu.service.TeacherService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/edu/front")
@CrossOrigin
@Api(tags = "前台首页课程展示")
public class IndexFrontController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;

    /*查询前八个热门课程，前4个热门讲师*/
    @GetMapping("index")
    @ApiOperation("查询前八个热门课程,前4个热门讲师")
    public Result index() {
        //查询前八个热门课程
        List<Course> list = courseService.courseIndex();
        //前4个热门讲师
        List<Teacher> list2 = teacherService.teacherIndex();
        return Result.ok().data("courseList",list).data("teacherList",list2);
    }


}
