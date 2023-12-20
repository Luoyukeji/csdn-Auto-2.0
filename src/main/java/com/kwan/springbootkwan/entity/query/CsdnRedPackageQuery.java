package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn红包查询参数类")
public class CsdnRedPackageQuery extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("内容id")
    private String contentId;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("红包出处")
    private String itemType;
    @ApiModelProperty("主题名称")
    private String topicTitle;
    @ApiModelProperty("分享链接")
    private String shareUrl;
    @ApiModelProperty("红包订单号")
    private String orderNo;
    @ApiModelProperty("我抢到的金额")
    private BigDecimal myAmount;

    @ApiModelProperty("我抢到的最小金额")
    private BigDecimal myAmountStart;
    @ApiModelProperty("我抢到的最大金额")
    private BigDecimal myAmountEnd;

    @ApiModelProperty("红包情况")
    private String msg;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @ApiModelProperty("红包创建时间")
    private String startDate;
}