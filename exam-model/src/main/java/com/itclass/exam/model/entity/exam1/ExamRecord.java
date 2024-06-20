package com.itclass.exam.model.entity.exam1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.itclass.exam.model.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**考试成绩实体类
 * @author: 徐泰森
 * @create: 2024-04-09 10:16
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ExamRecord extends BaseEntity {

//    @TableId(type = IdType.AUTO)
//    @ApiModelProperty(value = "主键 考试主键的id", example = "1")
    private Integer recordId;

//    @ApiModelProperty(value = "考试用户id", example = "1")
    private Integer userId;

//    @ApiModelProperty(value = "用户考试中的答案", example = "1")
    private String userAnswers;

//    @ApiModelProperty(value = "考试过程中的信用截图", example = "imgUrl")
    private String creditImgUrl;

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
//    @ApiModelProperty(value = "考试时间", example = "2020-10-20")
    private Date examTime;

//    @ApiModelProperty(value = "考试逻辑得分", example = "69")
    private Integer logicScore;

//    @ApiModelProperty(value = "考试的id", example = "1")
    private Integer examId;

//    @ApiModelProperty(value = "考试的题目id", example = "1")
    private String questionIds;

//    @ApiModelProperty(value = "考试总得分", example = "100")
    private Integer totalScore;

//    @ApiModelProperty(value = "考试错题id", example = "1,2,3")
    private String errorQuestionIds;

}