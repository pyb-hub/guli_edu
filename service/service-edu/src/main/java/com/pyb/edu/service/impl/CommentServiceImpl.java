package com.pyb.edu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Comment;
import com.pyb.edu.mapper.CommentMapper;
import com.pyb.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public HashMap<String, Object> getCommentPage(Page<Comment> page) {
        baseMapper.selectPage(page,null);
        List<Comment> records = page.getRecords();
        long total = page.getTotal();
        long pageCurrent = page.getCurrent();
        long size = page.getSize();
        long pages = page.getPages();
        boolean next = page.hasNext();
        boolean previous = page.hasPrevious();

        HashMap<String,Object> map = new HashMap<>();
        map.put("records",records);
        map.put("total",total);
        map.put("current",pageCurrent);
        map.put("size",size);
        map.put("next",next);
        map.put("previous",previous);
        map.put("pages",pages);
        return map;
    }
}
