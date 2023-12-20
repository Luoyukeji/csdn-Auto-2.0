package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.CsdnFollowFansInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;


@Data
public class CsdnFollowFansInfoDTO {

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
    @ApiModelProperty("发布时间")
    private Date postTime;
    @ApiModelProperty("是否需要通知,0不需要,1需要")
    private String needNotice;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnFollowFansInfoDTO, CsdnFollowFansInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}