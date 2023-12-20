package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn红包详情查询参数类")
public class CsdnRedPackageDetailQuery extends BasePage {
    @ApiModelProperty("主键id")
    private String orderNo;
    @ApiModelProperty("社区id")
    private String communityId;
    @ApiModelProperty("发布id")
    private String postId;
}