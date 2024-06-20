package com.itclass.exam.manager.service;

import cn.hutool.http.server.HttpServerResponse;
import com.itclass.exam.model.entity.product.Category;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    //分类列表 ，懒加载，每次查询只查一层数据
    List<Category> findByParentId(Long id);

    //导出（下载
    void exportData(HttpServletResponse response);

    //导出
    void importData(MultipartFile file);
}
