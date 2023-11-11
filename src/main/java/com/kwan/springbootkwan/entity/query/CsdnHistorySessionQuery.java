package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn三连用户查询参数类")
public class CsdnHistorySessionQuery extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户code")
    private String userName;
    @ApiModelProperty("CSDN用户名称")
    private String nickName;
    @ApiModelProperty("是否回复")
    private Integer hasReplied;
}