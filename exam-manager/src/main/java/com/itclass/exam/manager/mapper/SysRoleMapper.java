package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.dto.system.SysRoleDto;
import com.itclass.exam.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {

    //角色列表分页查询
    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    //添加角色
    void saveSysRole(SysRole sysRole);

    //修改角色
    void updateSysRole(SysRole sysRole);

    //删除角色
    void deleteById(Long roleId);

    //查询所有角色
    List<SysRole> findAll();
}
