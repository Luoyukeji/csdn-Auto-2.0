package com.kwan.springbootkwan.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("csdn三连用户更新参数类")
public class PicInfoUpdate {
    @ApiModelProperty("主键id")
    private List<Integer> ids;
    @ApiModelProperty("图片类型,0:表示宝宝图片,1:学习图片,2:风景,3:美女,99:其他")
    private String type;
}