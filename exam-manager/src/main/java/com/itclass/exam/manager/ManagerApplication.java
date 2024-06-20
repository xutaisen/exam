package com.itclass.exam.manager;


import com.itclass.exam.manager.propeties.MinioProperties;
import com.itclass.exam.manager.propeties.UserProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @author: 徐泰森
 * @create: 2024-03-19 12:38
 **/
//@EnableScheduling//多数据源使用此注解，避免自动装配
@SpringBootApplication
@ComponentScan(basePackages = {"com.itclass.exam"})//定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中
@EnableConfigurationProperties(value = {UserProperties.class , MinioProperties.class})//读取配置文件
//@MapperScan(basePackages = "com/itclass/exam/manager/mapper/*")
public class ManagerApplication {

    public static void main(String[] args) {

            SpringApplication.run(ManagerApplication.class,args);


    }
}