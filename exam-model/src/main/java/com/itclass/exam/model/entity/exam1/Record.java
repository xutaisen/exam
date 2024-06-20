package com.itclass.exam.model.entity.exam1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itclass.exam.model.entity.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 考试成绩实体类
 *
 * @author: 徐泰森
 * @create: 2024-05-04 16:22
 **/
@Data
public class Record extends BaseEntity {

    private Integer userId;

    private Integer examId;

    private String examName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private Date examTime;

    private Integer logicScore;

    private Integer passScore;

    private Integer totalScore;
}