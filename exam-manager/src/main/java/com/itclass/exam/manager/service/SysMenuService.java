package com.itclass.exam.manager.service;

import com.itclass.exam.model.entity.system.SysMenu;
import com.itclass.exam.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {

    //菜单列表
    List<SysMenu> findNodes();

    //添加菜单
    void save(SysMenu sysMenu);

    //修改
    void update(SysMenu sysMenu);

    //删除
    void removeById(Long id);

    //查询用户可以操作的菜单
    List<SysMenuVo> findUserMenuList();
}
