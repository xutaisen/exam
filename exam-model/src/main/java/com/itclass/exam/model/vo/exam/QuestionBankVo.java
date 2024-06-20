package com.itclass.exam.model.vo.exam;
import com.github.pagehelper.PageInfo;
import com.itclass.exam.model.entity.exam1.QuestionBank;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-13 14:57
 **/
@Data
public class QuestionBankVo {

    private QuestionBank questionBank;

    //分页数据列表
    private PageInfo<QuestionBank> pageInfo;

    //题库名
    private String bankName;

    //单选数量
    private Integer singleChoice;

    //多选数量
    private Integer multipleChoice;

    //判断数量
    private Integer judge;

    //简答数量
    private Integer shortAnswer;
}