package com.itclass.exam.model.entity.exam1;

import lombok.Data;

/**
 * 成绩答案详情
 * @author: 徐泰森
 * @create: 2024-05-08 15:48
 **/
@Data
public class RecordAnState {

    private Integer id;

    private Integer paperId;

    private Integer recordId;

    private Boolean isChecked;

    private Boolean isTrue;

    private Integer answerId;

    private String anContent;

}