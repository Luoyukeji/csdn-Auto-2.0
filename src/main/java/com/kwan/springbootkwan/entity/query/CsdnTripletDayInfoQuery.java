package com.kwan.springbootkwan.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn三连监控查询参数类")
public class CsdnTripletDayInfoQuery extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @ApiModelProperty("结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}