package com.itclass.exam.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.mapper.SysRoleMapper;
import com.itclass.exam.manager.mapper.SysRoleUserMapper;
import com.itclass.exam.manager.service.SysRoleService;
import com.itclass.exam.model.dto.system.SysRoleDto;
import com.itclass.exam.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 徐泰森
 * @create: 2024-03-23 11:03
 **/
@Service
public class SysRoleServiceImpl implements SysRoleService {


    @Autowired
    private SysRoleMapper sysRoleMapper ;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    //角色列表分页查询
    @Override
//    @Transactional
    public PageInfo<SysRole> findByPage(SysRoleDto sysRoleDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum , pageSize) ;
        List<SysRole> sysRoleList = sysRoleMapper.findByPage(sysRoleDto) ;
        PageInfo<SysRole> pageInfo = new PageInfo(sysRoleList) ;
        return pageInfo;
    }

    //添加角色
    @Override
    public void saveSysRole(SysRole sysRole) {
        sysRoleMapper.saveSysRole(sysRole);
    }

    //修改角色
    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRole(sysRole);
    }

    //删除角色
    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    //查询所有角色
    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //1 查询所有角色
        List<SysRole> sysRoleList = sysRoleMapper.findAll();

        //2 分配过的角色列表 根据userid查询出分配的角色
        List<Long> roleList = sysRoleUserMapper.findSysUserRoleByUserId(userId);

        HashMap<String, Object> map = new HashMap<>();
        map.put("allRolesList" , sysRoleList);
        map.put("sysUserRoles" , roleList);


        return map;
    }
}