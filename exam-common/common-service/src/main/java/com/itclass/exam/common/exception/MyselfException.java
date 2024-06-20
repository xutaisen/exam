package com.itclass.exam.common.exception;

import com.itclass.exam.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-03-21 15:19
 **/
//自定义的异常处理类
@Data
public class MyselfException extends RuntimeException {

    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;//枚举类

    //构造方法
    public MyselfException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

}