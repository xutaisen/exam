package com.itclass.exam.manager.service;

import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.dto.system.SysOperLogDto;
import com.itclass.exam.model.entity.system.SysOperLog;

public interface SysLogService {

    PageInfo<SysOperLog> findByPage(SysOperLogDto sysOperLogDto, Integer pageNum, Integer pageSize);
}
