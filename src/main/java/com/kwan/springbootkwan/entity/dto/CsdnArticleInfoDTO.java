package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Data
@ApiModel("csdn文章DTO")
public class CsdnArticleInfoDTO extends Model<CsdnArticleInfoDTO> {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("文章id")
    private String articleId;
    @ApiModelProperty("文章URL")
    private String articleUrl;
    @ApiModelProperty("文章分数")
    private String articleScore;
    @ApiModelProperty("文章标题")
    private String articleTitle;
    @ApiModelProperty("文章描述")
    private String articleDescription;
    @ApiModelProperty("文章类型")
    private String articleType;
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

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnArticleInfoDTO, CsdnArticleInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}