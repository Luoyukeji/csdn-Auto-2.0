package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.CsdnRedPackageDetailInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;


@Data
public class CsdnRedPackageDetailInfoDTO {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("红包订单号")
    private String orderNo;
    @ApiModelProperty("社区id")
    private Integer communityId;
    @ApiModelProperty("发布id")
    private Integer postId;
    @ApiModelProperty("用户名")
    private String receiverUserName;
    @ApiModelProperty("用户昵称")
    private String receiverNickName;
    @ApiModelProperty("抢到的金额")
    private Double receivedMoney;
    @ApiModelProperty("幸运王")
    private Integer lucky;
    @ApiModelProperty("领取时间")
    private Date receiveTime;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnRedPackageDetailInfoDTO, CsdnRedPackageDetailInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}