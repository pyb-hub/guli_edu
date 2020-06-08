package com.pyb.ucenter.controller;


import com.pyb.result.Result;
import com.pyb.ucenter.entity.Member;
import com.pyb.ucenter.entity.vo.RegisterVo;
import com.pyb.ucenter.service.MemberService;
import com.pyb.utils.JwtUtils;
import com.pyb.vo.OrderUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author pybCoding
 * @since 2020-06-04
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
@Api(tags = "登录注册")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("login")
    @ApiOperation("用户登录")/*登录成功返回token，前端把token放在cookie和request的请求头中*/
    public Result login(@RequestBody Member member) {
        /*前端在member传入mobile和password*/
        String token = memberService.login(member);
        return Result.ok().data("token",token);
    }

    @PostMapping("register")
    @ApiOperation("用户注册")
    public Result register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);
        return Result.ok();
    }

    @GetMapping("getInfoByToken")
    @ApiOperation("根据token得到用户信息")/*根据request中的token，获取用户信息，存入到cookie中*/
    public Result getInfoByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)){
            return Result.error().message("用户没有登录");
        }
        Member member = memberService.getById(id);
        return Result.ok().data("member",member);
    }

    @GetMapping("getCommentUserByToken")
    @ApiOperation("根据token得到用户id，姓名，头像")/*根据request中的token，获取用户信息，存入到cookie中*/
    public Result getCommentUserByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)){
            return Result.error().message("用户没有登录");
        }
        Member member = memberService.getById(id);
        return Result.ok().data("memberId",member.getId())
                .data("memberName",member.getNickname())
                .data("memberAvatar",member.getAvatar());
    }

    @GetMapping("getUserInfo/{userId}")
    @ApiOperation("根据userid得到用户信息")/*在订单页面调用该方法*/
    public OrderUserVo getUserInfo(@PathVariable("userId")String userId) {
        Member member = memberService.getById(userId);
        OrderUserVo userInfo = new OrderUserVo();
        BeanUtils.copyProperties(member,userInfo);
        return userInfo;
    }




}

