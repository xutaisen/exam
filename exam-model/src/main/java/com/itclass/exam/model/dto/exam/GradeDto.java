package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-09 22:07
 **/
@Data
@Schema(description = "请求参数实体类")
public class GradeDto {

    @Schema(description = "当前用户")
    private String userName ;

    @Schema(description = "搜索关键字")
    private String keyword;


}