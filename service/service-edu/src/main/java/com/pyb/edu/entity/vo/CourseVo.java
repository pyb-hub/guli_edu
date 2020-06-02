package com.pyb.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseVo {

    /*api注解是swagger接口开发的注解，在那个页面上显示辅助信息*/
    @ApiModelProperty(value = "课程标题，模糊查询")
    private String title;
    @ApiModelProperty(value = "课程状态")
    private String status;
    @ApiModelProperty(value = "课程发布时间，大于等于", example = "2019-01-01 10:10:10")
    private String publishTime;
    @ApiModelProperty(value = "更新时间，小于等于", example = "2019-01-01 10:10:10")
    private String updateTime;

}
