package com.itclass.exam.common.service;

import com.itclass.exam.model.entity.system.SysOperLog;

public interface AsyncOperLogService {
    // 保存日志数据
    public abstract void saveSysOperLog(SysOperLog sysOperLog);
}

