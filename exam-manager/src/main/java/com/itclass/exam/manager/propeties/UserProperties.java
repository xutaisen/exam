package com.itclass.exam.manager.propeties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**把拦截器不进行拦截的路径放到配置文件里再读取
 * @author: 徐泰森
 * @create: 2024-03-22 14:02
 **/
@Data
@ConfigurationProperties(prefix = "exam.auth")
public class UserProperties {

    private List<String> noAuthUrls;
}