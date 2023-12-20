package com.kwan.springbootkwan.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn红包总个数和领取个数DTO")
public class CsdnRedPackageReceiveInfoDTO {
    @ApiModelProperty("总个数")
    private Integer allCount;
    @ApiModelProperty("已经领取数")
    private Integer receivedCount;
}