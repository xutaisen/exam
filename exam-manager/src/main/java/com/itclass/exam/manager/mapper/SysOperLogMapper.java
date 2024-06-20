package com.itclass.exam.manager.mapper;

import com.itclass.exam.model.dto.system.SysOperLogDto;
import com.itclass.exam.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysOperLogMapper {


    void insert(SysOperLog sysOperLog);

//    分页查询日志
    List<SysOperLog> findByPage(SysOperLogDto sysOperLogDto);
}
