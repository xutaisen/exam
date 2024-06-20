package com.itclass.exam.manager.config;

import com.itclass.exam.manager.interceptor.LoginAuthInterceptor;
import com.itclass.exam.manager.propeties.UserProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: 徐泰森
 * @create: 2024-03-21 16:52
 **/
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginAuthInterceptor loginAuthInterceptor;

    @Autowired
    private UserProperties userProperties;

    /**
     *解决跨域
     */
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")      // 添加路径规则
                .allowCredentials(true)               // 是否允许在跨域的情况下传递Cookie
                .allowedOriginPatterns("*")           // 允许请求来源的域规则
                .allowedMethods("*")
                .allowedHeaders("*") ;                // 允许所有的请求头
    }


    /**
     * 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginAuthInterceptor)
                //.excludePathPatterns("/admin/system/index/login" , "/admin/system/index/generateValidateCode")//不拦截的路径（登录，获取验证码
                .excludePathPatterns(userProperties.getNoAuthUrls())
                .addPathPatterns("/**");//需要拦截的路径

    }
}