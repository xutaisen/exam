package com.itclass.exam.manager;

import com.alibaba.excel.EasyExcel;
import com.itclass.exam.model.vo.product.CategoryExcelVo;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-30 15:30
 **/

public class easyExcelTest {

    public static void main(String[] args) {
        read();
        write();
    }

    public static void read(){
        String fileName = "D://111111.xlsx" ;
        // 创建一个监听器对象
        ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>();
        EasyExcel.read(fileName, CategoryExcelVo.class, excelListener).sheet().doRead();// 解析excel表格（sheet每一行）
        List<CategoryExcelVo> excelVoList = excelListener.getDatas();    //获取解析到的数据
        excelVoList.forEach(s -> System.out.println(s) );   // 进行遍历操作
    }


    public static void write(){

    }
}