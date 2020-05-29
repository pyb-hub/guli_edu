package com.pyb.edu.controller;

import com.pyb.result.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

@Api(description = "讲师后台登录")
@RestController
@RequestMapping("/edu/user")
@CrossOrigin/*解决跨域问题，允许跨域访问*/
public class LoginController_edu {

    @PostMapping("login")
    public Result login_do(@RequestParam String username) {
        return Result.ok();
    }
}
