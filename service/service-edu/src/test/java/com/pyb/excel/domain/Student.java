package com.pyb.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor/*有参构造*/
@NoArgsConstructor
public class Student {

    /*设置表中对应的名称*/
    /*value：对应写操作写入的表头名称，第一行；index：读操作对应的表的列*/
    @ExcelProperty(value = "学生id",index = 0)
    private Integer id;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String name;
}
