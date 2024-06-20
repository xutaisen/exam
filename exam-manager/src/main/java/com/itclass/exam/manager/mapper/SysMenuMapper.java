package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {

    //1 查询所有菜单，返回list集合
    List<SysMenu> finAll();

    //添加菜单
    void save(SysMenu sysMenu);

    //修改
    void update();

    //删除
    void remove(Long id);

    //根据菜单id查询是否有子节点
    Integer countByParentId(Long id);

    //根据userId查询可以操作的菜单
    List<SysMenu> selectListByUserId(Long id);

    // 查询是否存在父节点
    SysMenu selectById(Long parentId);
}
