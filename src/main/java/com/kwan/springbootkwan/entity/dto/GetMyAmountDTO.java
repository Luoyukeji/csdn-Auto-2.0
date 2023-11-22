package com.kwan.springbootkwan.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class GetMyAmountDTO {
    @ApiModelProperty("今日所得")
    private BigDecimal todayGet;
    @ApiModelProperty("累计所得")
    private BigDecimal allGet;
}