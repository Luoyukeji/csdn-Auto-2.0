package com.kwan.springbootkwan.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kwan.springbootkwan.entity.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn余额参数类")
public class CsdnTotalIncomeQuery extends PageQuery {
    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @ApiModelProperty("结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
}