package com.kwan.springbootkwan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("分页实体类")
public class BasePage {
    @ApiModelProperty("页数")
    private Integer page;
    @ApiModelProperty("每页条数")
    private Integer pageSize;
}
