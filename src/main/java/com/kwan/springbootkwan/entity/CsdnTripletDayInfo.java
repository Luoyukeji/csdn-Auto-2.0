package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn每日三连监控类")
@TableName("csdn_triplet_day_info")
public class CsdnTripletDayInfo extends Model<CsdnTripletDayInfo> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("三连日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date tripletDate;
    @ApiModelProperty("星期")
    private String weekInfo;
    @ApiModelProperty("点赞数量")
    private Integer likeNum;
    @ApiModelProperty("收藏数量")
    private Integer collectNum;
    @ApiModelProperty("评论数量")
    private Integer commentNum;
    @ApiModelProperty("点赞状态")
    private Integer likeStatus;
    @ApiModelProperty("收藏状态")
    private Integer collectStatus;
    @ApiModelProperty("评论状态")
    private Integer commentStatus;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}