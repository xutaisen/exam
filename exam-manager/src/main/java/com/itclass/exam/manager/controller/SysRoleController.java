package com.itclass.exam.manager.controller;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.common.annotation.Log;
import com.itclass.exam.manager.service.SysRoleService;
import com.itclass.exam.model.dto.system.SysRoleDto;
import com.itclass.exam.model.entity.system.SysRole;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 角色
 *
 * @author: 徐泰森
 * @create: 2024-03-23 10:24
 **/
@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     *角色列表分页查询
     */
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@RequestBody SysRoleDto sysRoleDto,
                                                @PathVariable(value = "pageNum") Integer pageNum,
                                                @PathVariable(value = "pageSize") Integer pageSize) {

        PageInfo<SysRole> pageInfo = sysRoleService.findByPage(sysRoleDto, pageNum, pageSize);

        System.out.println(pageInfo.getList());
        System.out.println(pageInfo.getTotal());
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     *添加角色
     */
    @Log(title = "角色添加",businessType = 1) //添加Log注解，设置属性
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole sysRole){
        System.out.println("111111111111111111111"+sysRole);
        sysRoleService.saveSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 修改角色
     */
    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 删除角色
     */
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }


    /**
     * 查询所有角色
     */
    @GetMapping("/findAllRoles/{userId}")
    public Result findAllRoles(@PathVariable(value = "userId")Long userId){
        Map<String , Object> map = sysRoleService.findAllRoles(userId);
        return Result.build(map , ResultCodeEnum.SUCCESS);
    }
}