package com.pyb.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.pyb.result.Result;
import com.pyb.vod.service.VodService;
import com.pyb.vod.utils.InitAliyunClient;
import com.pyb.vod.utils.VodConstants;
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
    @ApiOperation("上传视频到阿里云")
    public Result uploadVideo(MultipartFile file) {
        /*上传视频,返回阿里云上视频的id，对应数据库的video_source_id*/
        String videoId = vodService.uploadVideo(file);
        if (!StringUtils.isEmpty(videoId)){
            return Result.ok().data("videoId",videoId);
        }
        return Result.error().data("videoId",null);
    }


    @DeleteMapping("removeVideo/{sourceId}")
    @ApiOperation("删除阿里云存储的视频")
    public Result removeVideo(@PathVariable("sourceId") String sourceId) {
        //sourceId对应数据库中的video_source_id
        Boolean b = vodService.delVideo(sourceId);
        if (b) {
            return Result.ok();
        }
        return Result.error().message("删除视频失败~");
    }

    @GetMapping("getPlayAuth/{sourceId}")
    @ApiOperation("通过sourceId获取视频播放凭证")
    public Result getPlayAuth(@PathVariable("sourceId") String sourceId) {
        /*sourceId对应数据库的video_source_id*/
        /*前端页面通过这个凭证来调用aliyun组件来播放视频（加密也可以），前端如果通过地址访问只能播放不加密的视频*/
        try {
            DefaultAcsClient client =
                    InitAliyunClient.initVodClient(VodConstants.ACCESS_KEY_ID, VodConstants.ACCESS_KEY_SECRET);
            /*1.获取client对象*/
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            /*2.向request设置id*/
            request.setVideoId(sourceId);
            /*3.调用方法得到凭证*/
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);

        } catch (ClientException e) {
            e.printStackTrace();
            return Result.error();
        }


    }
}
