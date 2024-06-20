package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-16 14:57
 **/
@Data
public class ExamTeacherDto {

    @Schema(description = "考试类型 开放 需要密码")
    private Integer selectValue;

    @Schema(description = "搜索关键字")
    private String keyword ;

    @Schema(description = "开始时间")
    private String createTimeBegin ;

    @Schema(description = "结束时间")
    private String createTimeEnd;
}