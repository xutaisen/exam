package com.itclass.exam.manager.service;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.dto.system.AssginRoleDto;
import com.itclass.exam.model.dto.system.LoginDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.system.LoginVo;

public interface SysUserService {

    //登录
    LoginVo login(LoginDto loginDto);

    //获取当前登录用户信息
    SysUser getUserInfo(String token);

    //用户退出
    void logout(String token);

    //用户条件查询分页接口
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    //新增用户
    void addUser(SysUser sysUser);

    //更改用户信息
    void updateSysUser(SysUser sysUser);

    //删除用户
    void deleteById(Long userId);

    //给用户分配角色
    void doAssign(AssginRoleDto assginRoleDto);

    PageInfo<SysUser> findAllStu(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);
}
