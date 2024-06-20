package com.itclass.exam.model.dto.exam;

import lombok.Data;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-05-02 14:39
 **/
@Data
public class PaperAnswerDto {

    //(value = "回答列表", required=true)
    private List<String> answers;

    // (value = "主观答案", required=true)
    private String answer;

    //(value = "试卷ID", required=true)
    private Integer paperId;

    //(value = "题目ID", required=true)
    private Integer quId;
}