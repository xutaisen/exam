package com.itclass.exam.manager.controller;

import com.itclass.exam.manager.service.SysMenuService;
import com.itclass.exam.manager.service.SysUserService;
import com.itclass.exam.manager.service.ValidateCodeService;
import com.itclass.exam.model.dto.system.LoginDto;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.model.vo.system.LoginVo;

import com.itclass.exam.model.vo.system.SysMenuVo;
import com.itclass.exam.model.vo.system.ValidateCodeVo;
import com.itclass.exam.util.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-03-19 12:48
 **/
@Tag(name = "用户接口")//swagger接口文档
@RestController
@RequestMapping(value = "/admin/system/index")
public class IndexController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ValidateCodeService validateCodeService;

    @Autowired
    private SysMenuService sysMenuService;

    //生成图片验证码
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }


    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public Result login(@RequestBody LoginDto loginDto) {//loginDto用来封装前端传递过来的数据
        LoginVo loginVo = sysUserService.login(loginDto);//LoginVo生成jwt令牌
        return Result.build(loginVo, ResultCodeEnum.SUCCESS);
    }


    //获取当前登录用户信息
//    @GetMapping(value = "/getUserInfo")
//    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
//        SysUser sysUser = sysUserService.getUserInfo(token) ;
//        return Result.build(sysUser , ResultCodeEnum.SUCCESS) ;
//    }
    //获取当前登录用户信息 的优化
    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo() {


        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS) ;
    }



    //退出登录
    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        sysUserService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }


    //查询用户可以操作的菜单
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findUserMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }
}