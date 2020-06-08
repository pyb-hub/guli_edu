package com.pyb.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.vo.CourseConfirmVo;
import com.pyb.edu.entity.vo.CourseInfo;
import com.pyb.edu.entity.vo.CourseVo;
import com.pyb.edu.service.CourseService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("CourseList/{page}/{size}")
    @ApiOperation("课程列表分页条件查询")
    public Result courseList(@PathVariable("page") int page,
                             @PathVariable("size") int size,
                             @RequestBody(required = false) CourseVo courseVo) {
        Page<Course> coursePage = courseService.courseList(page, size, courseVo);
        List<Course> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return Result.ok().data("records",records).data("total",total);
    }


    @DeleteMapping("delCourse/{id}")
    @ApiOperation("课程删除，还需要删除对应的章节，小节，视频")
    public Result delCourse(@PathVariable String id) {
        Boolean b =  courseService.delCourse(id);
        return b ? Result.ok():Result.error();
    }

    @PostMapping("add")
    @ApiOperation("添加课程")
    public Result addCourse(@RequestBody CourseInfo courseInfo) {
        String id = courseService.saveCourseInfo(courseInfo);
        /*前端界面需要用到id，所以返回id*/
        return Result.ok().data("courseId",id);
    }

    @GetMapping("findOne/{id}")
    @ApiOperation("根据id查询课程信息")
    public Result getCourse(@PathVariable String id) {
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

    @GetMapping("findConfirmInfo/{id}")
    @ApiOperation("根据id查询课程确认界面的信息")
    public Result getCourseConfirmInfo(@PathVariable String id) {
        CourseConfirmVo courseConfirmInfo = courseService.getCourseConfirmInfoById(id);
        return Result.ok().data("courseConfirmInfo",courseConfirmInfo);
    }

    @PutMapping("publish/{id}")
    @ApiOperation("修改课程状态：是否发布")
    public Result publishCourse(@PathVariable String id) {
        Course course = new Course();
        course.setId(id);
        /*修改发布课程标识*/
        course.setStatus("Normal");
        courseService.updateById(course);
        return Result.ok();
    }

}

