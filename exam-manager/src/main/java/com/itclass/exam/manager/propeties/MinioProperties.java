package com.itclass.exam.manager.propeties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: 徐泰森
 * @create: 2024-03-25 13:19
 **/
@Data
@ConfigurationProperties(prefix = "exam.minio")//读取配置文件节点
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secreKey;
    private String bucketName;
}