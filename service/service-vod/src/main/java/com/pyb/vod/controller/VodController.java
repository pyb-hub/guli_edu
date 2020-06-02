package com.pyb.vod.controller;

import com.pyb.result.Result;
import com.pyb.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "视频上传和删除接口")
@RestController
@RequestMapping("/edu/vod")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("uploadVideo")
    @ApiOperation("上传视频")
    public Result uploadVideo(MultipartFile file) {
        /*上传视频*/
        String videoId = vodService.uploadVideo(file);
        if (!StringUtils.isEmpty(videoId)){
            return Result.ok().data("videoId",videoId);
        }
        return Result.error().data("videoId",null);
    }

    /*TODO 删除视频*/
    @DeleteMapping("removeVideo/{sourceId}")
    @ApiOperation("删除视频")
    public Result removeVideo(@PathVariable("sourceId") String sourceId) {
        Boolean b = vodService.delVideo(sourceId);
        if (b) {
            return Result.ok();
        }
        return Result.error().message("删除视频失败~");
    }
}
