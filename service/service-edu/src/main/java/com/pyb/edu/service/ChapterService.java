package com.pyb.edu.service;

import com.pyb.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pyb.edu.entity.chapter.ChapterOne;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-05-30
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterOne> findChapterAndVideo(String cid);
}
