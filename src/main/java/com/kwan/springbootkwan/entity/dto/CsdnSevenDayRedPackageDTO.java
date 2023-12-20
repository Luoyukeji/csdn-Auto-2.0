package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("csdn七日红包概览DTO")
public class CsdnSevenDayRedPackageDTO extends Model<CsdnSevenDayRedPackageDTO> {
    @ApiModelProperty("我抢到的总金额")
    private BigDecimal myAmount;
    @ApiModelProperty("红包个数")
    private Integer redPackageCount;
    @ApiModelProperty("平均每天金额")
    private BigDecimal averageDay;
    @ApiModelProperty("平均每个金额")
    private BigDecimal averageOne;
}