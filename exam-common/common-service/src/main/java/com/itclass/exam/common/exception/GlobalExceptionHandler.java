package com.itclass.exam.common.exception;

import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: 徐泰森
 * @create: 2024-03-21 15:11
 **/

@ControllerAdvice//controller增强器，给controller增加统一操作和处理
public class GlobalExceptionHandler {


    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json数据
    public Result error(Exception e){
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }

    //自定义异常处理
    @ExceptionHandler(MyselfException.class)
    @ResponseBody
    public Result error(MyselfException e){
        return Result.build(null,e.getResultCodeEnum());
    }
}