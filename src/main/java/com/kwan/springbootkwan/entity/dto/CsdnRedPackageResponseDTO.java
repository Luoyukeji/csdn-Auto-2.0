package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn红包信息组装后DTO")
public class CsdnRedPackageResponseDTO {
    @ApiModelProperty("分页信息")
    private PageBean<CsdnRedPackageDTO> from;
    @ApiModelProperty("今日信息")
    private GetMyAmountDTO getMyAmountDTO;
}