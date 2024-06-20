package com.itclass.exam.model.vo.exam;

import com.itclass.exam.model.entity.base.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: 徐泰森
 * @create: 2024-05-03 09:36
 **/
@Data
public class QuestionPaperVo extends BaseEntity {
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
}