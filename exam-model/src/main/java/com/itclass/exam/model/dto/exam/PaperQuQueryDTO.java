package com.itclass.exam.model.dto.exam;

import com.itclass.exam.model.entity.base.BaseEntity;
import lombok.Data;

/**
 * @author: 徐泰森
 * @create: 2024-04-27 21:45
 **/
@Data

public class PaperQuQueryDTO extends BaseEntity {


    private Integer paperId;


    private Integer quId;

}