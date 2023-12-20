package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("csdn余额信息返回DTO")
public class CsdnAccountManagementAllDTO {
    @ApiModelProperty("当前累计")
    private BigDecimal currentTotal;
    @ApiModelProperty("当前余额")
    private BigDecimal currentAmount;
    @ApiModelProperty("可过期")
    private BigDecimal expireAmount;
    @ApiModelProperty("永久有效")
    private BigDecimal normalAmount;
    @ApiModelProperty("累计收入")
    private BigDecimal totalGetAmount;
    @ApiModelProperty("累计支出")
    private BigDecimal totalPayAmount;
    @ApiModelProperty("余额分页信息")
    private PageBean<CsdnAccountManagementDTO> from;
}