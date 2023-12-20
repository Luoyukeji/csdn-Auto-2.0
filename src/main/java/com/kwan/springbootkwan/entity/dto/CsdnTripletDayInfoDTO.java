package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel("csdn三连监控DTO")
public class CsdnTripletDayInfoDTO extends Model<CsdnTripletDayInfoDTO> {
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

    @ApiModelProperty("每日金额")
    private BigDecimal myAmount = BigDecimal.ZERO;
    @ApiModelProperty("红包个数")
    private Integer redPackageCount = 0;
    @ApiModelProperty("日平均金额")
    private BigDecimal averageDaily = BigDecimal.ZERO;
    @ApiModelProperty("丢包个数")
    private Integer loseRedPackageCount = 0;
    @ApiModelProperty("理论最大金额")
    private BigDecimal theoryAmount = BigDecimal.ZERO;

    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnTripletDayInfoDTO, CsdnTripletDayInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}