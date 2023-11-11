package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn文章查询参数类")
public class CsdnArticleInfoQuery extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户code")
    private String userName;
    @ApiModelProperty("CSDN用户名称")
    private String nickName;
    @ApiModelProperty("CSDN文章id")
    private String articleId;
    @ApiModelProperty("CSDN文章URL")
    private String articleUrl;
    @ApiModelProperty("点赞状态")
    private Integer likeStatus = 0;
    @ApiModelProperty("收藏状态")
    private Integer collectStatus = 0;
    @ApiModelProperty("评论状态")
    private Integer commentStatus = 0;
    @ApiModelProperty("是否是自己的文章,默认0不是,1是")
    private Integer isMyself;
    @ApiModelProperty("添加类型")
    private Integer addType;
    @ApiModelProperty("质量分开始")
    private Integer articleScoreStart;
    @ApiModelProperty("质量分结束")
    private Integer articleScoreEnd;
}