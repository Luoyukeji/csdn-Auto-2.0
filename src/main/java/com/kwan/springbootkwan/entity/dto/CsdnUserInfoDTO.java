package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Data
@ApiModel("csdn三连用户DTO")
public class CsdnUserInfoDTO extends Model<CsdnUserInfoDTO> {

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

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnUserInfoDTO, CsdnUserInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}