package com.itclass.exam.model.vo.exam;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-05-06 17:39
 **/
@Data
public class RecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String userName;

    private String quContent;

    private Boolean isRight;//是否正确

    private List<RecordAnVo> answerVos;


}