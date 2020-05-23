package com.pyb.config.exceptionHandler;

import com.pyb.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/*捕获其他模块的controller中的异常，需要引入该模块的依赖*/
@ControllerAdvice
@Slf4j/*代表引入异常日志的对象log*/
public class GlobalExceptionHandler {


    /*全局异常处理*/
    @ExceptionHandler(Exception.class)
    @ResponseBody/*页面返回自定义json数据*/
    public Result error(Exception e) {
        /*error输出到日志中*/
        log.error(e.getMessage());

        e.printStackTrace();
        return Result.error().message("全局异常处理");
    }

    /*特定指定的异常处理*/
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody/*页面返回自定义json数据*/
    public Result error(ArithmeticException e) {
        /*error输出到日志中*/
        log.error(e.getMessage());

        e.printStackTrace();
        return Result.error().message("全局异常处理");
    }

    /*自定义的异常处理，需要自定义异常类*/
    @ExceptionHandler(MyException.class)
    @ResponseBody/*页面返回自定义json数据*/
    public Result error(MyException e) {
        /*error输出到日志中*/
        log.error(e.getMessage());

        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }

}
