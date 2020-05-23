package com.pyb.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherVo {

    /*api注解是swagger接口开发的注解，在那个页面上显示辅助信息*/
    @ApiModelProperty(value = "讲师姓名，模糊查询")
    private String name;
    @ApiModelProperty(value = "讲师级别，大于或者等于")
    private Integer level;
    @ApiModelProperty(value = "讲师入驻时间时间，大于等于", example = "2019-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "讲师更新时间，小于等于", example = "2019-01-01 10:10:10")
    private String end;

}
