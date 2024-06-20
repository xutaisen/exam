package com.itclass.exam.manager.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.itclass.exam.manager.service.CategoryService;
import com.itclass.exam.model.entity.product.Category;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-30 12:08
 **/
@RestController
@RequestMapping(value="/admin/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     *分类列表 ，懒加载，每次查询只查一层数据
     */
    @GetMapping("/findByParentId/{parentId}")
    public Result findByParentId(@PathVariable(value = "parentId") Long parentId){

        List<Category> categoryList = categoryService.findByParentId(parentId);
        return  Result.build(categoryList , ResultCodeEnum.SUCCESS);
    }

    /**
     * 导出(下载
     */
    @GetMapping("/exportData")
    public void exportData(HttpServletResponse response){
        categoryService.exportData(response);
    }

    /**
     * 导入
     */
    @PostMapping("importData")
    public Result importData(MultipartFile file) {
        categoryService.importData(file);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}