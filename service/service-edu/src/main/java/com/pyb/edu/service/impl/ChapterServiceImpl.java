package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pyb.edu.entity.Chapter;
import com.pyb.edu.entity.Video;
import com.pyb.edu.entity.chapter.ChapterOne;
import com.pyb.edu.entity.chapter.VideoTwo;
import com.pyb.edu.mapper.ChapterMapper;
import com.pyb.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pyb.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterOne> findChapterAndVideo(String cid) {
        /*查找所有的一级分类*/
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",cid);
        wrapper.orderByAsc("gmt_create");
        List<Chapter> listChapter = baseMapper.selectList(wrapper);
        List<ChapterOne> chapterOnes = new ArrayList<>();

        /*查找一级分类对应的所有的二级分类*/
        for (Chapter c : listChapter) {
            /*把查到的一级实体类，转化为我们自己封装的一级分类*/
            ChapterOne chapterOne = new ChapterOne();
            BeanUtils.copyProperties(c,chapterOne);

            /*查找对应的二级分类*/
            String id = c.getId();
            QueryWrapper<Video> wrapper2 = new QueryWrapper<>();
            wrapper2.eq("chapter_id",id);
            wrapper2.eq("course_id",cid);
            List<Video> listVideo = videoService.list(wrapper2);

            /*把查到的二级实体类，转化为我们自己封装的一级分类中的二级分类*/
            List<VideoTwo> children = new ArrayList<>();
            for (Video v : listVideo) {
                VideoTwo videoTwo = new VideoTwo();
                BeanUtils.copyProperties(v,videoTwo);
                children.add(videoTwo);
            }
            /*封装到树形结构中*/
            chapterOne.setChildren(children);
            /*返回的自己封装的树形结构结果集*/
            chapterOnes.add(chapterOne);
        }
        return chapterOnes;
    }
}
