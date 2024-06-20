package com.itclass.exam.manager.mapper;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface SysUserMapper {

    //根据 用户名 查询数据库表 sys_user表
    SysUser selectUserInfoByUserName(String userName);

    //用户条件查询分页接口
    List<SysUser> findByPage(SysUserDto sysUserDto);

    //新增用户
    void addUser(SysUser sysUser);

    //更改用户信息
    void updateSysUser(SysUser sysUser);

    //删除用户
    void deleteById(Long userId);


    List<SysUser> findAllStu(SysUserDto sysUserDto);

//    //根据名字找id
//    Integer byId(String name);
//
//    //根据用户找id
//    String byUser(SysUser sqlUser);
}
