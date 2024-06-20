package com.itclass.exam.model.dto.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**交卷dto
 * @author: 徐泰森
 * @create: 2024-05-04 17:22
 **/
@Data
public class GoHomeDto {

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone="GMT+8")
    private String examTime;


    private Integer paperId;
}