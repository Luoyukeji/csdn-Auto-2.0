package com.kwan.springbootkwan.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class GetMyAmountDTO {
    @ApiModelProperty("今日所得")
    private BigDecimal todayGet;
    @ApiModelProperty("今日所得红包个数")
    private Integer todayCount;
    @ApiModelProperty("累计所得")
    private BigDecimal allGet;
    @ApiModelProperty("累计所得红包个数")
    private Integer allCount;
    @ApiModelProperty("今日平均")
    private BigDecimal averageDay;
    @ApiModelProperty("每个平均")
    private BigDecimal averageOne;
}