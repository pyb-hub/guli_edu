package com.pyb.edu.entity.vo;

import lombok.Data;

@Data
public class CourseConfirmVo {

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    /*一级标签*/
    private String subjectOne;
    /*二级标签*/
    private String subjectTwo;
    private String teacherName;
    private String price;
    private String description;
}

