package com.pyb.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
public interface CommentService extends IService<Comment> {

    HashMap<String,Object> getCommentPage(Page<Comment> page);
}
