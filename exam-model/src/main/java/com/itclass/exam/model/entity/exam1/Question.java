package com.itclass.exam.model.entity.exam1;

import com.itclass.exam.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 徐泰森
 * @create: 2024-04-14 20:37
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question extends BaseEntity {

    @Schema(description = "题目内容")
    private String quContent;

    @Schema(description = "创建人")
    private String createPerson;

    @Schema(description = "题目类型")
    private Integer quType;

    @Schema(description = "题目难度")
    private Integer level;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "所属题库id")
    private String quBankId;

    @Schema(description = "所属题库名")
    private String quBankName;

    @Schema(description = "题目解析")
    private String analysis;

    private Integer sort;//序号

//    @ApiModelProperty(value = "是否已答", required=true)
    private Boolean answered;

    //是否正确
    private Boolean isRight;

    //实际评分
    private Integer actualScore;

    //分值
    private Integer score;

    //主观答案
    private String answer;


}