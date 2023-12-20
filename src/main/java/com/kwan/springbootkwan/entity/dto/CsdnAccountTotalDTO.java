package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("csdn余额累计DTO")
public class CsdnAccountTotalDTO extends Model<CsdnAccountTotalDTO> {
    @ApiModelProperty("类型")
    private Integer operateType;
    @ApiModelProperty("总金额")
    private BigDecimal amount;
}