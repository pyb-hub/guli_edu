package com.pyb.excel;

/*读操作需要监听器，来一行行的读取数据*/

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.pyb.excel.domain.Student;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<Student> {

    /*一行行读取内容，从第二行开始读取，不读表头标题*/
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        System.out.println("----"+student);
    }

    /*读取表头信息*/
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        System.out.println("--表头--"+headMap);
    }

    /*读完之后执行*/
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }


}
