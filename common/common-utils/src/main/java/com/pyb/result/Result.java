package com.pyb.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*统一结果返回类*/
@Data
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    private Result(){}

    public static Result ok(){
        Result r = new Result();
        r.setSuccess(true);
        /*20000代表成功码*/
        r.setCode(20000);
        r.setMessage("成功!");
        return r;
    }

    public static Result error(){

        Result r = new Result();
        r.setSuccess(false);
        /*30000代表失败码*/
        r.setCode(30000);
        r.setMessage("失败");
        return r;
    }

    /*属性的get方法，返回this，链式调用*/
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }

}
