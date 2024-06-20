package com.itclass.exam.manager.controller;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.service.SysUserService;
import com.itclass.exam.model.dto.system.AssginRoleDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 徐泰森
 * @create: 2024-03-24 12:55
 **/

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService sysUserService;


    /**
     * 用户条件查询分页接口
     */
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(SysUserDto sysUserDto,
                             @PathVariable(value = "pageNum") Integer pageNum,
                             @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 用户添加
     */
    @PostMapping(value = "/saveSysUser")
    public Result addUser(@RequestBody SysUser sysUser) {
        sysUserService.addUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 用户修改
     */
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 用户删除
     */
    @DeleteMapping("/deleteById/{userId}")
    public Result deleteById(@PathVariable(value = "userId") Long userId) {
        sysUserService.deleteById(userId);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }

    /**
     * 给用户分配角色
     * 保存分配数据
     */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto) {
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }


    /**
     * 查询学生
     */
    @GetMapping("/findAllStu/{pageNum}/{pageSize}")
    public Result findAllStu(SysUserDto sysUserDto,
                             @PathVariable(value = "pageNum") Integer pageNum,
                             @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findAllStu(sysUserDto, pageNum, pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);

    }
}