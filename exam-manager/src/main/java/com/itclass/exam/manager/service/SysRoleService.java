package com.itclass.exam.manager.service;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.dto.system.SysRoleDto;
import com.itclass.exam.model.entity.system.SysRole;

import java.util.Map;

public interface SysRoleService {

    //角色列表分页查询
    PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize);

    //添加角色
    void saveSysRole(SysRole sysRole);

    //修改角色
    void updateSysRole(SysRole sysRole);

    //删除角色
    void deleteById(Long roleId);

    //查询所有角色
    Map<String, Object> findAllRoles(Long userId);
}
