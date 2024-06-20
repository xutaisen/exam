package com.itclass.exam.model.dto.exam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author: 徐泰森
 * @create: 2024-04-15 10:22
 **/
@Data
public class AssginBankDto {

    @Schema(description = "试题id")
    private Long id;

    @Schema(description = "题库id的List集合")
    private List<String> banksIdList;
}