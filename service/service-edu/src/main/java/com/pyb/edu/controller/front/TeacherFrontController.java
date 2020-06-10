package com.pyb.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.Teacher;
import com.pyb.edu.service.CourseService;
import com.pyb.edu.service.TeacherService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/edu/front/teacher")
@Api(tags = "讲师前台页面展示")
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping("findAll/{currentPage}/{size}")
    @ApiOperation("讲师分页查询所有")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,
                          @PathVariable("size") Integer size) {
        /*分页查询，返回所有的分页数据，因为前端是需要所有的页面page的数据,自己做分页功能，所有返回所有信息的map；
        * 也可以用前端的框架分页功能，只需要传回total和teacherList*/
        HashMap<String,Object> teacherInfo = teacherService.getPageInfo(currentPage,size);
        return Result.ok().data(teacherInfo);
    }

    @ApiOperation("根据id查询教师详情")
    @GetMapping("findById/{id}")
    public Result findById(@PathVariable String id){
        /*查询teacher和他的course信息*/
        Teacher byId = teacherService.getById(id);
        if (byId == null){
            return Result.error();
        }else {
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
            wrapper.eq("teacher_id",id);
            wrapper.orderByDesc("view_count");
            List<Course> courseList = courseService.list(wrapper);
            return Result.ok().data("teacher",byId).data("courseList",courseList);
        }
    }


}
