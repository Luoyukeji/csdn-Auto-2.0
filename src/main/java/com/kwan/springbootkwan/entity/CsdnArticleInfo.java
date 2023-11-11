package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn文章类")
@TableName("csdn_article_info")
public class CsdnArticleInfo extends Model<CsdnArticleInfo> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("文章id")
    private String articleId;
    @ApiModelProperty("文章URL")
    private String articleUrl;
    @ApiModelProperty("文章分数")
    private Integer articleScore;
    @ApiModelProperty("文章标题")
    private String articleTitle;
    @ApiModelProperty("文章描述")
    private String articleDescription;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("点赞状态")
    private Integer likeStatus;
    @ApiModelProperty("收藏状态")
    private Integer collectStatus;
    @ApiModelProperty("评论状态")
    private Integer commentStatus;
    @ApiModelProperty("是否是自己的文章,默认0不是,1是")
    private Integer isMyself;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

