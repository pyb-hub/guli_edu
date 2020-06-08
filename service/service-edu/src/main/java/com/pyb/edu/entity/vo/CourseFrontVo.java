package com.pyb.edu.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseFrontVo {

    /*api注解是swagger接口开发的注解，在那个页面上显示辅助信息*/
    @ApiModelProperty(value = "课程标题，模糊查询")
    private String title;
    @ApiModelProperty(value = "讲师Id")
    private String teacherId;
    @ApiModelProperty(value = "一级分类Id")
    private String subjectParentId;
    @ApiModelProperty(value = "二级分类Id")
    private String subjectId;
    @ApiModelProperty(value = "时间排序")
    private String gmtCreateSort;
    @ApiModelProperty(value = "价格排序")
    private String priceSort;
    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

}
