package com.itclass.exam.model.vo.exam;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-04-27 20:49
 **/
@Data
public class PaperQuVo implements Serializable {

//    @ApiModelProperty(value = "题目内容", required=true)
    private String content;

    //题目类型
    private Integer quType;

    //题目序号
    private Integer sort;

//    @ApiModelProperty(value = "答案内容", required=true)
    List<AnswerVo> answerList;
}