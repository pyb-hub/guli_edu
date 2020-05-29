package com.pyb.excel;


import com.alibaba.excel.EasyExcel;
import com.pyb.excel.domain.Student;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {

    @Test
    public void testWrite() {
        String fileName = "/Users/panyibiao/Desktop/1.xlsx";
        List<Student> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Student(i,"test"+i));
        }
        EasyExcel.write(fileName,Student.class).sheet("表一").doWrite(list);
    }
    @Test
    public void testRead() {
        String fileName = "/Users/panyibiao/Desktop/1.xlsx";

        EasyExcel.read(fileName,Student.class,new ExcelListener()).sheet("表一").doRead();
    }
}
