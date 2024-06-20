package com.itclass.exam.model.entity.exam1;

import lombok.Data;

/**
 * 成绩题目状态表
 * @author: 徐泰森
 * @create: 2024-05-08 15:45
 **/
@Data
public class RecordQuState {

    private Integer id;

    private Integer paperId;

    private Integer recordId;

    private Boolean isRight;

    private Integer quId;

    private String quContent;

}