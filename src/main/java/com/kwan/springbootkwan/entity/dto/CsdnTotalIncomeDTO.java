package com.kwan.springbootkwan.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("csdn累计红包金额DTO")
public class CsdnTotalIncomeDTO {
    @ApiModelProperty("抢到的金额")
    private BigDecimal amount;
    @ApiModelProperty("红包个数")
    private String nickName;
}