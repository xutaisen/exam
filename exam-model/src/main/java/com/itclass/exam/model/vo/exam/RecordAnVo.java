package com.itclass.exam.model.vo.exam;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 徐泰森
 * @create: 2024-05-06 18:17
 **/
@Data
public class RecordAnVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer answerId;

    private String anContent;//答案内容

    private Boolean isTrue;//是否是正确答案

    private Boolean isChecked;//是否选中

}