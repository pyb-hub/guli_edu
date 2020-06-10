package com.pyb.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pyb.edu.client.UcenterClient;
import com.pyb.edu.entity.Comment;
import com.pyb.edu.service.CommentService;
import com.pyb.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;
import java.util.HashMap;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-07
 */
@Api(tags = "前端课程详情页评论接口")
@RestController
@RequestMapping("/edu/front/comment")
//@CrossOrigin  和gateway里面处理二选一，不能处理二次
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("getAllPage/{current}/{limit}")
    @ApiOperation("分页查询所有的评论")
    public Result getAll(@PathVariable("current") Integer current,
                         @PathVariable("limit") Integer limit) {
        /*current:当前的页数，limit一页有多少数据,相当于size*/
        Page<Comment> page = new Page<>(current,limit);
        HashMap<String,Object> commentPage = commentService.getCommentPage(page);
        return Result.ok().data(commentPage);

    }

    @PostMapping("saveComment")
    @ApiOperation("保存评论")
    public Result saveComment(@RequestBody Comment comment, HttpServletRequest request) {
        /*远程调用，获取request的header中的user信息；也可以在前端页面获取*/
        Result result = ucenterClient.getCommentUserByToken(request);
        String memberId = (String) result.getData().get("memberId");
        String nickName = (String) result.getData().get("memberName");
        String avatar = (String) result.getData().get("memberAvatar");

        /*在后端传入用户信息，前端只获取课程信息，讲师信息，评论信息*/
        comment.setMemberId(memberId).setAvatar(avatar).setNickname(nickName);
        /*保存到数据库*/
        boolean save = commentService.save(comment);
        return save ? Result.ok() : Result.error();
    }


}

