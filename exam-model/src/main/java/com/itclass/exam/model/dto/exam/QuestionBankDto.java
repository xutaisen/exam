package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**题库请求参数实体类
 * @author: 徐泰森
 * @create: 2024-04-13 14:26
 **/
@Data
public class QuestionBankDto {

    @Schema(description = "搜索关键字")
    private String keyword ;

}