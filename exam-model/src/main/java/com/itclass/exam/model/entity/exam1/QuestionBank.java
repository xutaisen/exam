package com.itclass.exam.model.entity.exam1;

import com.itclass.exam.model.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**题库实体类
 * @author: 徐泰森
 * @create: 2024-04-13 14:18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor

public class QuestionBank extends BaseEntity {


//主键 题库id
//    private Integer id;

//   题库名称
    private String bankName;

}