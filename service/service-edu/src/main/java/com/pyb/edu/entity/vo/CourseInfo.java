package com.pyb.edu.entity.vo;

import com.pyb.edu.entity.Course;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseInfo extends Course {

    @ApiModelProperty("课程简介")
    private String description;
}

