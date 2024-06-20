package com.itclass.exam.manager.service.impl;

import com.alibaba.excel.EasyExcel;
import com.itclass.exam.common.exception.MyselfException;
import com.itclass.exam.manager.listener.ExcelListener;
import com.itclass.exam.manager.mapper.CategoryMapper;
import com.itclass.exam.manager.service.CategoryService;
import com.itclass.exam.model.entity.product.Category;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.model.vo.product.CategoryExcelVo;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-30 12:10
 **/
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    //导出、下载
    @Override
    public void exportData(HttpServletResponse response) {

        try {

            // 设置响应结果类型
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");

            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("分类数据", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            //response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");

            // 查询数据库中的数据
            List<Category> categoryList = categoryMapper.selectAll();
            List<CategoryExcelVo> categoryExcelVoList = new ArrayList<>(categoryList.size());

            // 将从数据库中查询到的Category对象转换成CategoryExcelVo对象
            for(Category category : categoryList) {
                CategoryExcelVo categoryExcelVo = new CategoryExcelVo();
                BeanUtils.copyProperties(category, categoryExcelVo, CategoryExcelVo.class);
                categoryExcelVoList.add(categoryExcelVo);
            }

            // 写出数据到浏览器端
            EasyExcel.write(response.getOutputStream(), CategoryExcelVo.class).sheet("分类数据").doWrite(categoryExcelVoList);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //导出
    @Override
    public void importData(MultipartFile file) {
        try {
            //创建监听器对象，传递mapper对象
            ExcelListener<CategoryExcelVo> excelListener = new ExcelListener<>(categoryMapper);
            //调用read方法读取excel数据
            EasyExcel.read(file.getInputStream(),
                    CategoryExcelVo.class,
                    excelListener).sheet().doRead();
        } catch (IOException e) {
            throw new MyselfException(ResultCodeEnum.DATA_ERROR);
        }
    }


    //分类列表 ，懒加载，每次查询只查一层数据
    @Override
    public List<Category> findByParentId(Long parentId) {

        //根据分类id查询它下面的所有的子分类数据
        List<Category> categoryList = categoryMapper.finByParenId(parentId);

        if (!CollectionUtils.isEmpty(categoryList)){//如果不为空
            categoryList.forEach(item ->{
               int count = categoryMapper.countByParentId(item.getId());
               if (count > 0){
                   item.setHasChildren(true);
               }else {
                   item.setHasChildren(false);
               }
            });
        }
        return categoryList;
    }




}