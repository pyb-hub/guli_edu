package com.pyb.edu.service.impl;

import com.pyb.edu.client.VodClient;
import com.pyb.edu.entity.Video;
import com.pyb.edu.mapper.VideoMapper;
import com.pyb.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyb.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    /*注入vod服务*/
    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeVideo(String id) {
        /*删除小节和视频*/
        Video video = this.getById(id);
        String videoSourceId = video.getVideoSourceId();
        if (!StringUtils.isEmpty(videoSourceId)){
            /*删除阿里云的视频*/
            vodClient.removeVideo(videoSourceId);
        }
        /*删除小节：不能先删除，不然上面的removeVideo查不到videoSourceId*/
        this.removeById(id);
        return true;
    }
}
