package com.itclass.exam.manager.service;

import com.itclass.exam.model.dto.system.AssginMenuDto;

import java.util.Map;

public interface SysRoleMenuService {

    //菜单
    Map<String, Object> findSysRoleMenuByRoleId(Long roleId);

    //保存角色分配菜单数据
    void doAssign(AssginMenuDto assginMenuDto);
}
