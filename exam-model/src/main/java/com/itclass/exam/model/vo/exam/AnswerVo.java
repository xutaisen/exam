package com.itclass.exam.model.vo.exam;

import com.itclass.exam.model.entity.exam1.Answer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: 徐泰森
 * @create: 2024-04-27 21:11
 **/
@Data
public class AnswerVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String content;

//    @ApiModelProperty(value = "回答项ID", required=true)
    private Integer answerId;

//    @ApiModelProperty(value = "题目ID", required=true)
    private Integer quId;

//    @ApiModelProperty(value = "是否正确项", required=true)
    private Boolean isRight;

//    @ApiModelProperty(value = "是否选中", required=true)
    private Boolean checked;

//    @ApiModelProperty(value = "排序", required=true)
    private Integer sort;

//    @ApiModelProperty(value = "选项标签", required=true)
    private String abc;
}