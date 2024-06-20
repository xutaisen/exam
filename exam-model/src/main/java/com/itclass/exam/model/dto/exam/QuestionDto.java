package com.itclass.exam.model.dto.exam;

import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-14 20:31
 **/
@Data
public class QuestionDto {

    private Integer type;

    private String bankName;

    private String keyword;
}