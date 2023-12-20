package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn红包详细信息类")
@TableName("csdn_red_package_detail_info")
public class CsdnRedPackageDetailInfo extends Model<CsdnRedPackageDetailInfo> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("红包订单号")
    private String orderNo;
    @ApiModelProperty("社区id")
    private Integer communityId;
    @ApiModelProperty("发布id")
    private Integer postId;
    @ApiModelProperty("用户名")
    private String receiverUserName;
    @ApiModelProperty("用户昵称")
    private String receiverNickName;
    @ApiModelProperty("抢到的金额")
    private BigDecimal receivedMoney;
    @ApiModelProperty("幸运王")
    private Integer lucky;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("领取时间")
    private Date receiveTime;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

