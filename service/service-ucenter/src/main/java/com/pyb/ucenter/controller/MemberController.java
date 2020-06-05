package com.pyb.ucenter.controller;


import com.pyb.result.Result;
import com.pyb.ucenter.entity.Member;
import com.pyb.ucenter.entity.vo.RegisterVo;
import com.pyb.ucenter.service.MemberService;
import com.pyb.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("用户登录")
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
    @ApiOperation("根据token得到用户信息")
    public Result getInfoByToken(HttpServletRequest request) {
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(id)){
            return Result.error().message("用户没有登录");
        }
        Member member = memberService.getById(id);
        return Result.ok().data("member",member);
    }


}

