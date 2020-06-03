package com.pyb.edu.controller;


import com.pyb.edu.entity.Video;
import com.pyb.edu.service.VideoService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Api(tags = "章节下的小节管理")
@RestController
@RequestMapping("/edu/video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("addVideo")
    @ApiOperation("添加小节")
    public Result addVideo(@RequestBody Video video) {
        boolean save = videoService.save(video);
        if (save){
            return Result.ok();
        }
        return Result.error();
    }

    @PostMapping("updateVideo")
    @ApiOperation("修改小节")
    public Result updateVideo(@RequestBody Video video) {
        boolean b = videoService.updateById(video);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }

    @DeleteMapping("delVideo/{id}")
    @ApiOperation("通过小节id删除小节")
    public Result delVideo(@PathVariable String id) {
        /*删除小节的时候要把小节里面的视频删除了*/
        boolean b = videoService.removeVideo(id);
        if (b){
            return Result.ok();
        }
        return Result.error();
    }



}

