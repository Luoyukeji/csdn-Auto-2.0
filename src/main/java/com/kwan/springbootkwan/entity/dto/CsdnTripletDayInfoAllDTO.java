package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.PageBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn三连监控总信息返回DTO")
public class CsdnTripletDayInfoAllDTO {
    @ApiModelProperty("日三连监控分页信息")
    private PageBean<CsdnTripletDayInfoDTO> from;
}