package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn每日红包DTO")
public class CsdnDayRedPackageDTO extends Model<CsdnDayRedPackageDTO> {

    @ApiModelProperty("红包日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date redPackageDate;
    @ApiModelProperty("我抢到的金额")
    private BigDecimal myAmount;
    @ApiModelProperty("红包个数")
    private Integer redPackageCount;
    @ApiModelProperty("丢包个数")
    private Integer loseRedPackageCount;
}