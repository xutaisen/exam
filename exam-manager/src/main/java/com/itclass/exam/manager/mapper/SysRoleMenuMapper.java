package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.dto.system.AssginMenuDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMenuMapper {
    ////给角色分配菜单
    List<Long> findSysRoleMenuByRoleId(Long roleId);

    // 根据角色的id删除其所对应的菜单数据
    void deleteByRoleId(Long roleId);


    void doAssign(AssginMenuDto assginMenuDto);

    // 将该id的菜单设置为半开
    void updateSysRoleMenuIsHalf(Long id);
}
