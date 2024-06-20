package com.itclass.exam.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itclass.exam.manager.mapper.SysOperLogMapper;
import com.itclass.exam.manager.service.SysLogService;
import com.itclass.exam.model.dto.system.SysOperLogDto;
import com.itclass.exam.model.dto.system.SysUserDto;
import com.itclass.exam.model.entity.system.SysOperLog;
import com.itclass.exam.model.entity.system.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-04-05 12:50
 **/
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    /**
     *用户条件查询分页接口
     */
    @Override
    public PageInfo<SysOperLog> findByPage(SysOperLogDto sysOperLogDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysOperLog> list = sysOperLogMapper.findByPage(sysOperLogDto);
        PageInfo<SysOperLog> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}