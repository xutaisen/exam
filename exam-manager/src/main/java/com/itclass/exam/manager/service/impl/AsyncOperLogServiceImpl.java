package com.itclass.exam.manager.service.impl;

import com.itclass.exam.common.service.AsyncOperLogService;
import com.itclass.exam.manager.mapper.SysOperLogMapper;
import com.itclass.exam.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: 徐泰森
 * @create: 2024-04-04 16:09
 **/
@Service
public class AsyncOperLogServiceImpl implements AsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;


    @Async      // 异步执行保存日志操作
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }

}