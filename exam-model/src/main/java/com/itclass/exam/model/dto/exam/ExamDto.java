package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-07 23:34
 **/
@Data
@Schema(description = "请求参数实体类")
public class ExamDto {


    @Schema(description = "搜索关键字")
    private String keyword ;


}