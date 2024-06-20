package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleUserMapper {
    //1 根据userId删除用户之前分配的角色数据
    void deleteById(Long userId);

    //2 重新分配新数据
    void doAssign(Long userId, Long roleId);

    //2 分配过的角色列表 根据userid查询出分配的角色
    List<Long> findSysUserRoleByUserId(Long userId);
}
