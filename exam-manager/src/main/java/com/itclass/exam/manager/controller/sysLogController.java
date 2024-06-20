package com.itclass.exam.manager.controller;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.service.SysLogService;
import com.itclass.exam.model.dto.system.SysOperLogDto;
import com.itclass.exam.model.dto.system.SysRoleDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysOperLog;
import com.itclass.exam.model.entity.system.SysRole;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: 徐泰森
 * @create: 2024-04-05 12:48
 **/
@RestController
@RequestMapping(value="/admin/system/sysLog")
public class sysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 日志条件查询分页接口
     */
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result findByPage(SysOperLogDto sysOperLogDto ,
                             @PathVariable(value = "pageNum") Integer pageNum ,
                             @PathVariable(value = "pageSize") Integer pageSize){
        PageInfo<SysOperLog> pageInfo = sysLogService.findByPage(sysOperLogDto,pageNum,pageSize);
        return Result.build(pageInfo, ResultCodeEnum.SUCCESS);
    }
}