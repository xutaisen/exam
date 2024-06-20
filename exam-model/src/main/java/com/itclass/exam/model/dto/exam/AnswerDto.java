package com.itclass.exam.model.dto.exam;

import com.itclass.exam.model.entity.exam1.Answer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-04-20 15:50
 **/
@Data
public class AnswerDto implements Serializable {


    private static final long serialVersionUID = 1L;


    //是否是正确答案
    private String isTrue;

    //答案内容
    private String content;

    //解析
    private String analysis;

    //题目内容
    private String name;

}