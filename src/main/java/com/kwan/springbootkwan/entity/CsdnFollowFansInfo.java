package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("csdn关注与粉丝信息")
@TableName("csdn_follow_fans_info")
public class CsdnFollowFansInfo extends Model<CsdnFollowFansInfo> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("博客地址")
    private String blogUrl;
    @ApiModelProperty("与我的关系")
    private String relationType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

