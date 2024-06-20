package com.itclass.exam.manager.controller;

import com.itclass.exam.common.annotation.Log;
import com.itclass.exam.common.enums.OperatorType;
import com.itclass.exam.manager.service.SysMenuService;
import com.itclass.exam.model.entity.system.SysMenu;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-26 09:26
 **/
@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //菜单添加
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }

    //菜单修改
    @PutMapping("/updateById")
    public Result update(@RequestBody SysMenu sysMenu){
        sysMenuService.update(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }


    //菜单删除
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable(value = "id") Long id){
        sysMenuService.removeById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS);
    }


    //菜单列表
    @Log(title = "菜单列表",businessType = 0,operatorType = OperatorType.MANAGE)
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return  Result.build(list , ResultCodeEnum.SUCCESS);
    }

}