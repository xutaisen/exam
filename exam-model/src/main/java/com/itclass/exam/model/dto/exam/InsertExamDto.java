package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-18 22:46
 **/
@Data
//添加考试的题目
public class InsertExamDto {

    @Schema(description = "考试名字")
    private String examName ;

    @Schema(description = "题目id")
    private String questionId ;

    @Schema(description = "分值")
    private String score ;
}