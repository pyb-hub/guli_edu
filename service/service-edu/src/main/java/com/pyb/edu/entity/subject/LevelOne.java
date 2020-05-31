package com.pyb.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*封装成前端对应的树形展示格式*/
@Data
public class LevelOne {
    private String id;
    private String title;
    /*多个二级分类*/
    private List<LevelTwo> children = new ArrayList<>();

}
