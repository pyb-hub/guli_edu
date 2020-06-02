package com.pyb.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.pyb.vod.service.VodService;
import com.pyb.vod.utils.InitAliyunClient;
import com.pyb.vod.utils.VodConstants;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class VodServiceImpl implements VodService {

    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //上传视频原始名称
            String originalFilename = file.getOriginalFilename();
            //上传之后显示的名称
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            //获取文件的输入流
            InputStream inputStream = file.getInputStream();

            UploadStreamRequest request = new UploadStreamRequest(VodConstants.ACCESS_KEY_ID, VodConstants.ACCESS_KEY_SECRET, title, originalFilename, inputStream);
            UploadVideoImpl uploadVideo = new UploadVideoImpl();
            UploadStreamResponse response = uploadVideo.uploadStream(request);

            String videoId =null;
            /*如果设置回调URL无效，不影响视频上传，还可以返回id和错误码*/
            if (response.isSuccess()){
                videoId = response.getVideoId();
            }else {
                videoId = response.getVideoId();
            }

            return videoId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean delVideo(String id)  {
        /*删除阿里云上面的视频*/
        try {
            /*调用自定义的初始化工具类，初始化client*/
            DefaultAcsClient client = InitAliyunClient.initVodClient(VodConstants.ACCESS_KEY_ID, VodConstants.ACCESS_KEY_SECRET);
            /*创建删除视频的request对象*/
            DeleteVideoRequest request = new DeleteVideoRequest();
            /*向request设置视频id*/
            request.setVideoIds(id);
            /*调用初始化对象进行删除*/
            client.getAcsResponse(request);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
