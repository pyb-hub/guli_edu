package com.pyb.edu.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectExcel {

    @ExcelProperty(index = 0)/*读取第一列的数据:一级分类*/
    private String subjectOne;
    @ExcelProperty(index = 1)/*读取第二列的数据:二级分类*/
    private String subjectTwo;

}
