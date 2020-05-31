package com.pyb.edu.controller;


import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.vo.CourseInfo;
import com.pyb.edu.service.CourseService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Api(tags = "课程管理接口")
@RestController
@RequestMapping("/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("add")
    @ApiOperation("添加课程")
    public Result addCourse(@RequestBody CourseInfo courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);
        /*前端界面需要用到id，所以返回id*/
        return Result.ok().data("courseId",id);
    }

    @GetMapping("findOne/{id}")
    @ApiOperation("根据id查询课程信息")
    public Result addCourse(@PathVariable String id) {
        CourseInfo course = courseService.getCourseInfoById(id);
        return Result.ok().data("course",course);
    }

    @PostMapping("update")/*用post方式相当于重新提交表单*/
    @ApiOperation("修改课程信息")
    public Result updateCourse(@RequestBody CourseInfo courseInfo) {
        String id = courseService.updateCourseInfo(courseInfo);
        /*前端界面需要用到id，所以返回id*/
        return Result.ok().data("courseId",id);
    }



}

