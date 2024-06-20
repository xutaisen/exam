package com.itclass.exam.common.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: 徐泰森
 * @create: 2024-03-18 22:11
 **/
@Configuration//配置类
public class knife4jConfig {
    @Bean
    public GroupedOpenApi adminApi() {      // 创建了一个api接口的分组
        System.out.println("6666666666666666666666666666");
        return GroupedOpenApi.builder()
                .group("admin-api")         // 分组名称
                .pathsToMatch("/admin/**")  // 接口请求路径规则
                .build();
    }



    /***
     * @description 自定义接口信息
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("考试系统API接口文档")
                        .version("1.0")
                        .description("考试系统API接口文档")
                        .contact(new Contact().name("xts"))); // 设定作者
    }



}





//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Swagger2配置信息
// * 这里分了两组显示
// * 第一组是api，当作用户端接口
// * 第二组是admin，当作后台管理接口
// * 也可以根据实际情况来减少或者增加组
// *
// * @author Eric
// * @date 2023-07-30 22:17
// */
//
//@Configuration
//
//public class knife4jConfig {
//
//    private ApiInfo adminApiInfo() {
//        return new ApiInfoBuilder()
//                .title("Eric-SpringBoot整合Knife4j-API文档")
//                .description("本文档描述了SpringBoot如何整合Knife4j")
//                .version("1.0")
//                .contact(new Contact("Eric", "https://blog.csdn.net/weixin_47316183?type=blog", "ericsyn@foxmail.com"))
//                .build();
//    }
//
//    private ApiInfo webApiInfo() {
//        return new ApiInfoBuilder()
//                .title("Eric-SpringBoot整合Knife4j-API文档")
//                .description("本文档描述了SpringBoot如何整合Knife4j")
//                .version("1.0")
//                .contact(new Contact("Eric", "https://blog.csdn.net/weixin_47316183?type=blog", "ericsyn@foxmail.com"))
//                .build();
//    }
//
//    /**
//     * 第一组：admin
//     * @return
//     */
//    @Bean
//    public Docket webApiConfig() {
//        List<Parameter> pars = new ArrayList<>();
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        tokenPar.name("userId")
//                .description("用户token")
//                //.defaultValue(JwtHelper.createToken(1L, "admin"))
//                .defaultValue("1")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//        pars.add(tokenPar.build());
//
//        Docket webApi = new Docket(DocumentationType.SWAGGER_2)
//                .groupName("用户端接口")
//                .apiInfo(webApiInfo())
//                .select()
//                //只显示api路径下的页面
//                .apis(RequestHandlerSelectors.basePackage("com.itclass.exam.manager.controller"))
//                .paths(PathSelectors.regex("/admin/.*/.*"))
//                .build()
//                .globalOperationParameters(pars);
//        return webApi;
//    }
//
//    /**
//     * 第二组：admin
//     * @return
//     */
//    @Bean
//    public Docket adminApiConfig() {
//        List<Parameter> pars = new ArrayList<>();
//        ParameterBuilder tokenPar = new ParameterBuilder();
//        tokenPar.name("adminId")
//                .description("用户token")
//                .defaultValue("1")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build();
//        pars.add(tokenPar.build());
//
//        Docket adminApi = new Docket(DocumentationType.SWAGGER_2)
//                .groupName("后台接口")
//                .apiInfo(adminApiInfo())
//                .select()
//                //只显示admin路径下的页面
//                .apis(RequestHandlerSelectors.basePackage("com.eric.springbootknife4j"))
//                .paths(PathSelectors.regex("/admin/.*"))
//                .build()
//                .globalOperationParameters(pars);
//        return adminApi;
//    }
//
//}



//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.bind.annotation.RequestMethod;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.builders.ResponseMessageBuilder;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.ResponseMessage;
//import springfox.documentation.service.Tag;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 自定义版 Knife4j 组件配置文件.
// */
//@Configuration
//@EnableSwagger2WebMvc
//public class knife4jConfig {
//
//    @Bean(value = "defaultApi3")
//    public Docket defaultApi2() {
//
//        List<ResponseMessage> responseMessageList = new ArrayList<>();
//        responseMessageList.add(new ResponseMessageBuilder().code(200).message("Success").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(400).message("参数错误").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(401).message("没有认证").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(403).message("没有访问权限").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(404).message("找不到资源").build());
//        responseMessageList.add(new ResponseMessageBuilder().code(500).message("服务器内部错误").build());
//
//        ApiInfo apiInfoBuilder = new ApiInfoBuilder().title("Knife4j swagger-bootstrap-ui RESTful APIs")
//                .description("Knife4j swagger-bootstrap-ui RESTful APIs ~~~~~")
//                .termsOfServiceUrl("http://www.xxx.com")
//                .contact(new Contact("张三", "http://www.moon.com", "xx@qq.com"))
//                .version("1.0")
//                .build();
//
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .useDefaultResponseMessages(false)
//                .globalResponseMessage(RequestMethod.GET, responseMessageList)
//                .globalResponseMessage(RequestMethod.POST, responseMessageList)
//                .globalResponseMessage(RequestMethod.PUT, responseMessageList)
//                .globalResponseMessage(RequestMethod.DELETE, responseMessageList)
//                .apiInfo(apiInfoBuilder)
//                .tags(new Tag("baidu", "百度接口API"), getTags())
//                //分组名称
//                .groupName("2.X版本")
//                .enable(true)
//
//                .select()
//                //这里指定Controller扫描包路径
////                .apis(RequestHandlerSelectors.basePackage("com.moon.controller"))
//                .apis(RequestHandlerSelectors.basePackage("com.itclass.exam.manager.controller"))
//                .paths(PathSelectors.any())
//                .build();
//
//        return docket;
//    }
//
//
//    private Tag[] getTags() {
//
//        return new Tag[] {
//                new Tag("alibaba", "阿里巴巴API接口"),
//                new Tag("tencent", "腾讯API接口")
//        };
//    }
//
//}