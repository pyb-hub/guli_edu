package com.pyb.edu.controller.front;

import com.pyb.edu.client.OrderClient;
import com.pyb.edu.entity.chapter.ChapterOne;
import com.pyb.edu.entity.vo.CourseFrontDetailVo;
import com.pyb.edu.entity.vo.CourseFrontVo;
import com.pyb.edu.service.ChapterService;
import com.pyb.edu.service.CourseService;
import com.pyb.result.Result;
import com.pyb.utils.JwtUtils;
import com.pyb.vo.OrderCourseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/edu/front/course")
@CrossOrigin
@Api(tags = "课程前台页面展示")
public class CourseFrontController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("CourseList/{currentPage}/{size}")
    @ApiOperation("前端课程分页条件查询")
    public Result findAll(@PathVariable("currentPage") Integer currentPage,
                          @PathVariable("size") Integer size,
                          @RequestBody(required = false)CourseFrontVo courseFrontVo) {
        /*分页查询，返回所有的分页数据*/
        HashMap<String,Object> courseInfo = courseService.getPageInfo(currentPage,size,courseFrontVo);
        return Result.ok().data(courseInfo);
    }

    @GetMapping("findConfirmInfo/{courseId}")
    @ApiOperation("根据id查询课程详情界面的信息")
    public Result getCourseConnfo(@PathVariable String courseId, HttpServletRequest request) {
        //多表查询获取详情页面信息
        CourseFrontDetailVo courseFrontDetailInfo = courseService.getCourseFrontDetailInfoById(courseId);
        /*通过课程id获取课程章节和小节方法，调用之前的方法，返回树状结构*/
        List<ChapterOne> chapterAndVideo = chapterService.findChapterAndVideo(courseId);
        courseFrontDetailInfo.setChapterAndVideo(chapterAndVideo);
        if (JwtUtils.checkToken(request)){
            /*登录之后*/
            /*判断课程是否已经被购买*/
            int status = (Integer) orderClient.checkOrder(courseId, request).getData().get("status");
            courseFrontDetailInfo.setStatus(status);
        }
        return Result.ok().data("courseFrontDetailInfo",courseFrontDetailInfo);
    }

    /*被订单远程调用的方法*/
    @GetMapping("findCourseInfo/{courseId}")
    @ApiOperation("根据id查询课程的信息")
    public OrderCourseVo findCourseInfo(@PathVariable("courseId") String courseId) {
        OrderCourseVo orderCourseVo = courseService.getOrderCourseById(courseId);
        return orderCourseVo;
    }



}
