package com.pyb.edu.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*章节*/
@Data
public class ChapterOne {

    private String id;

    private String title;

    List<VideoTwo> children = new ArrayList<>();
}
