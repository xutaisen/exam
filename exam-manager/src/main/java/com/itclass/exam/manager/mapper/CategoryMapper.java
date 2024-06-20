package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    //分类列表 ，懒加载，每次查询只查一层数据
    List<Category> finByParenId(Long parentId);

    //根据id查子节点
    Integer countByParentId(Long id);

    //查询所有分类数据
    List<Category> selectAll();

    //批量存储的方法
    void batchInsert(List<Category> categoryList);
}
