package com.kwan.springbootkwan.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn余额参数类")
public class CsdnAccountManagementQuery extends BasePage {
    @ApiModelProperty("开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    @ApiModelProperty("结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("金额")
    private BigDecimal amount;
    @ApiModelProperty("金额业务类型")
    private String product;
    @ApiModelProperty("业务code,7000:红包收入")
    private Integer code;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("过期时间")
    private String expireTime;
    @ApiModelProperty("1收入 2支出")
    private Integer operateType;
    @ApiModelProperty("详情")
    private String description;
    @ApiModelProperty("时间")
    private String time;
    @ApiModelProperty("业务名称")
    private String productName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("删除标志")
    private Integer isDelete;
}