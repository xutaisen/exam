package com.itclass.exam.model.entity.exam1;

import com.itclass.exam.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-19 20:58
 **/
@Data
public class Answer extends BaseEntity {
    //  对应数据库的主键(uuid,自增id,雪花算法, redis,zookeeper)
//    @TableId(type = IdType.AUTO)
//    @ApiModelProperty(value = "主键 答案id", example = "1")
//    private Integer id;

//    @ApiModelProperty(value = "所有的选项信息", example = "2,3,4,5(代表ABCD四个选项)")
    private String allOption;

//    @ApiModelProperty(value = "答案的图片列表", example = "img1URl, img2URl")
    private String images;

//    @ApiModelProperty(value = "答案解析", example = "1+1=2")
    private String analysis;

//    @ApiModelProperty(value = "问题id", example = "1")
    private Integer questionId;

//    @ApiModelProperty(value = "正确的答案的索引", example = "allOption[index]")
    private String trueOption;

}