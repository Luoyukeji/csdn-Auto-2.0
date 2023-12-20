package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn红包累计信息类")
@TableName("csdn_red_package_total_rank_info")
public class CsdnRedPackageTotalRankInfo extends Model<CsdnRedPackageTotalRankInfo> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String receiverNickName;
    @ApiModelProperty("领取时间")
    private Date receiveTime;
    @ApiModelProperty("金额")
    private BigDecimal receivedMoney;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

