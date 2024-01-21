package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("csdn三连用户查询参数类")
public class CsdnUserInfoQuery extends BasePage {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("用户code")
    private String userName;
    @ApiModelProperty("CSDN用户名称")
    private String nickName;
    @ApiModelProperty("点赞状态")
    private Integer likeStatus = 0;
    @ApiModelProperty("收藏状态")
    private Integer collectStatus = 0;
    @ApiModelProperty("评论状态")
    private Integer commentStatus = 0;
    @ApiModelProperty("用户权重")
    private Integer userWeight;
    @ApiModelProperty("文章类型")
    private String articleType;
    @ApiModelProperty("用户主页")
    private String userHomeUrl;
    @ApiModelProperty("当前文章")
    private String currBlogUrl;
    @ApiModelProperty("添加类型")
    private Integer addType;
}