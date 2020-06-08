package com.pyb.edu.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pyb.edu.entity.Course;
import com.pyb.edu.entity.CourseDescription;
import com.pyb.edu.entity.chapter.ChapterOne;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CourseFrontDetailVo {

    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程名称")
    private String title;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "是否被购买")
    private int status;

    @ApiModelProperty("课程描述")
    private CourseDescription courseDescription;

    @ApiModelProperty("讲师姓名")
    private String teacherName;
    @ApiModelProperty("讲师信息")
    private String teacherInfo;
    @ApiModelProperty("讲师头像")
    private String teacherAvatar;

    @ApiModelProperty("一级分类名称")
    private String subjectParentName;
    @ApiModelProperty("一级分类id")
    private String subjectParentId;
    @ApiModelProperty("二级分类名称")
    private String subjectName;
    @ApiModelProperty("二级分类名id")
    private String subjectId;

    @ApiModelProperty("章节和小节")
    private List<ChapterOne> chapterAndVideo;

}
