package com.itclass.exam.manager;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: 徐泰森
 * @create: 2024-03-30 15:33
 **/
public class ExcelListener<T> extends AnalysisEventListener<T> {

    //可以通过实例获取该值
    private List<T> datas = new ArrayList<>();

    //读取Excel表格， 默认从第二行开始读取，第一行默认为表头
    @Override
    public void invoke(T o, AnalysisContext analysisContext) {//每读取一行,就调用一次方法
        datas.add(o);
    }


    public List<T> getDatas() {
        return datas;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // excel解析完毕以后需要执行的代码
    }
}