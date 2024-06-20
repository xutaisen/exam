package com.itclass.exam.manager.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itclass.exam.common.exception.MyselfException;
import com.itclass.exam.manager.mapper.SysRoleUserMapper;
import com.itclass.exam.manager.mapper.SysUserMapper;
import com.itclass.exam.manager.service.SysUserService;
import com.itclass.exam.model.dto.system.AssginRoleDto;
import com.itclass.exam.model.dto.system.LoginDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.model.vo.system.LoginVo;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: 徐泰森
 * @create: 2024-03-19 12:52
 **/
@Service
public class SysUserServiceImpl implements SysUserService {


    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Override
    public LoginVo login(LoginDto loginDto) {


        //获取输入验证码和存储到redis的key名称 loginDto获取到
        String key = loginDto.getCodeKey();//前端提交验证码的key
        String captcha = loginDto.getCaptcha();//前端提交的验证码

        //2根据获取的redis里面key，查询redis里面存储验证码
        String redisCode = redisTemplate.opsForValue().get("user:validate" + key);

        //3比较输入的验证码和redis 存储验证码是否-致
        if(StrUtil.isAllEmpty(redisCode) || !StrUtil.equalsIgnoreCase(redisCode,captcha)){//前者判断是否为空 后者判断是否一致（忽略大小写
            throw new MyselfException(ResultCodeEnum.VALIDATECODE_ERROR);
        }

        //如果输入的验证码和redis 存储验证码一致，则删除Redis中的验证码
        redisTemplate.delete("user:validate" + key);
//----------------------------------------------------------------------------------------------------------------------//
        //1 获取提交的用户名 ，loginDto获取到
        String userName = loginDto.getUserName();

        //2 根据用户名查询数据库表 sys_user表
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        System.out.println("进来了~~~~~~~·");
        //3 查询不到用户名，用户不存在1，返回错误结果
        if (sysUser == null) {//用户名不存在
            throw new MyselfException(ResultCodeEnum.LOGIN_ERROR);
        }

        //4 查询到用户，存在
        //5 获取输入的密码，比较输入的密码和数据库密码是否一致
        String dateBase_password = sysUser.getPassword();
        String input_password = loginDto.getPassword();

        //因为数据库的密码是加密的，所以要把输入进来的密码加密再比较 MD5
        input_password = DigestUtils.md5DigestAsHex(input_password.getBytes());
        if (!input_password.equals(dateBase_password)) {
            throw new MyselfException(ResultCodeEnum.LOGIN_ERROR);
        }

//        //如果输入的验证码和redis 存储验证码一致，则删除Redis中的验证码
//        redisTemplate.delete("user:validate" + key);

        //6 密码一致 登录成功，
        //7 登陆成功获取唯一标识token
        String token = UUID.randomUUID().toString().replaceAll("-", "");

        // 8 把登陆成功用户信息放到Redis里
        redisTemplate.opsForValue().
                set("user:login" + token,//key
                        JSON.toJSONString(sysUser),//values
                        7,//超时时间
                        TimeUnit.DAYS//时间单位（day.house..
                );

        //9 返回loginvo对象
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }


    /**
     * 获取当前登录用户信息
     * @param token
     * @return
     */
    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);

        //使用fastjson的方法把字符串转换成对象
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);

        return sysUser;
    }


    /**
     *  用户退出
     * @param token
     */
    @Override
    public void logout(String token) {

        //在Redis中把key删除
        redisTemplate.delete("user:login" + token);

    }

    /**
     *用户条件查询分页接口
     */
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
       List<SysUser> list = sysUserMapper.findByPage(sysUserDto);
       PageInfo<SysUser> pageInfo = new PageInfo<>(list);
       return pageInfo;
    }



    /**
     *新增用户
     */
    @Override
    public void addUser(SysUser sysUser) {

        //1 判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser sqlUserName = sysUserMapper.selectUserInfoByUserName(userName);
        if (sqlUserName != null){
            throw new MyselfException(ResultCodeEnum.USER_NAME_IS_EXISTS);//209 用户名存在
        }

        //2 给密码加密
        String md5_password = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes());
        sysUser.setPassword(md5_password);

        //设置Status的值 1为可用 0为停用
        sysUser.setStatus(1);
        sysUserMapper.addUser(sysUser);
    }

    /**
     *更改用户信息
     */
    @Override
    public void updateSysUser(SysUser sysUser) {
        //1 判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser sqlUser = sysUserMapper.selectUserInfoByUserName(userName);

//        String sqlName = sysUserMapper.byUser(sqlUser);
//        int userId = sysUserMapper.byId(userName);
//        int sqlId = sysUserMapper.byId(sqlName);

        if (sqlUser != null ){
            throw new MyselfException(ResultCodeEnum.USER_NAME_IS_EXISTS);//209 用户名存在
        }
        sysUserMapper.updateSysUser(sysUser);

    }


    /**
     *删除用户
     */
    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }



    @Override
    public PageInfo<SysUser> findAllStu(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> list = sysUserMapper.findAllStu(sysUserDto);
        PageInfo<SysUser> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /**
     *给用户分配角色
     */
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
       //1 根据userId删除用户之前分配的角色数据
        sysRoleUserMapper.deleteById(assginRoleDto.getUserId());

        //2 重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for (Long roleId : roleIdList){
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId() , roleId);
        }
    }


}