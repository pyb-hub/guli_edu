package com.pyb.edu.controller;

import com.pyb.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/*用springSercurity登录替换*/

/*
@Api(description = "讲师后台登录")
@RestController
@RequestMapping("/edu/user")
public class LoginController_edu {

    @PostMapping("login")
    public Result login_do(@RequestParam String username) {
        return Result.ok();
    }
}
*/
