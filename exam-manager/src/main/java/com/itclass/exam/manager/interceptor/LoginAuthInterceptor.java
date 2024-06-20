package com.itclass.exam.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itclass.exam.model.entity.system.SysUser;
import com.itclass.exam.model.vo.common.Result;
import com.itclass.exam.model.vo.common.ResultCodeEnum;
import com.itclass.exam.util.AuthContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**拦截器
 * @author: 徐泰森
 * @create: 2024-03-22 11:18
 **/
@Slf4j
@Component
public class LoginAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override//目标方法运行前执行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1 获取请求方式
        //如果请求方式是options 预检请求，直接放行
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }


        //2 从请求头获取token
        log.info("拦截器拦截到请求：{}", request.getRequestURL());
        String token = request.getHeader("token");

        //3 如果请求头为空，返回错误信息
        if (StrUtil.isEmpty(token)){
            responseNoLoginInfo(response);
            log.info("此请求请求头为空：{}", request.getRequestURL());
            return false;
        }

        //4 如果token不为空，拿着token查询Redis
        String s = redisTemplate.opsForValue().get("user:login" + token);

        //5 如果Redis查不到数据，返回错误信息
        if (StrUtil.isEmpty(s)){
            responseNoLoginInfo(response);
            log.info("请求查询不到Redis：{}", request.getRequestURL());
            return false;
        }

        //6 如果Redis查询到用户信息，把用户信息放到ThreadLocal里
        SysUser sysUser = JSON.parseObject(s, SysUser.class);
        AuthContextUtil.set(sysUser);

        //7 把Redis用户信息数据更新过期时间
        redisTemplate.expire("user:login" + token,24, TimeUnit.HOURS);

        //放行
        log.info("拦截器放行：{}", request.getRequestURL());
        return true;
    }



    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }



    @Override//目标方法运行后执行
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        //删除ThreadLocal
        AuthContextUtil.remove();
    }
}