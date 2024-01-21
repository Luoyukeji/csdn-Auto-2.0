package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("csdn用户类")
@TableName("csdn_user_info")
public class CsdnUserInfo extends Model<CsdnUserInfo> implements Serializable {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户code")
    private String userName;
    @ApiModelProperty("CSDN用户名称")
    private String nickName;
    @ApiModelProperty("点赞状态")
    private Integer likeStatus;
    @ApiModelProperty("收藏状态")
    private Integer collectStatus;
    @ApiModelProperty("评论状态")
    private Integer commentStatus;
    @ApiModelProperty("用户权重")
    private Integer userWeight;
    @ApiModelProperty("用户主页")
    private String userHomeUrl;
    @ApiModelProperty("当前文章")
    private String currBlogUrl;
    @ApiModelProperty("文章类型")
    private String articleType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}