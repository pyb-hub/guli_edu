package com.pyb.edu.service.impl;

import com.pyb.edu.entity.Video;
import com.pyb.edu.mapper.VideoMapper;
import com.pyb.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
