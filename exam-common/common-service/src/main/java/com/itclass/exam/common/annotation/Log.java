package com.itclass.exam.common.annotation;

import com.itclass.exam.common.enums.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})//表明此注解能作用的地方 方法上
@Retention(RetentionPolicy.RUNTIME) //生命周期 -- 运行时

public @interface Log { // 自定义操作日志记录注解

    public String title() ;								// 模块名称
    public OperatorType operatorType() default OperatorType.MANAGE;	// 操作人类别
    public int businessType() ;     // 业务类型（0其它 1新增 2修改 3删除）
    public boolean isSaveRequestData() default true;   // 是否保存请求的参数
    public boolean isSaveResponseData() default true;  // 是否保存响应的参数
}
