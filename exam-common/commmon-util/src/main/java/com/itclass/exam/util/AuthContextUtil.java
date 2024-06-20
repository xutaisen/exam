package com.itclass.exam.util;

import com.itclass.exam.model.entity.system.SysUser;

/**创建一个AuthContextUtil类对ThreadLocal进行封装，
 *
 * ThreadLocal是jdk所提供的一个线程工具类，叫做线程变量，
 * 意思是ThreadLocal中填充的变量属于当前线程，该变量对其他线程而言是隔离的，也就是说该变量是当前线程独有的变量，
 * 使用该工具类可以实现在同一个线程进行数据的共享。
 * @author: 徐泰森
 * @create: 2024-03-22 11:07
 **/

public class AuthContextUtil {

    //创建threadLocal对象
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<>();


    //添加数据
    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    //查询数据
    public static SysUser get(){
        return threadLocal.get();
    }


    //删除数据
    public static void remove(){
        threadLocal.remove();
    }

}