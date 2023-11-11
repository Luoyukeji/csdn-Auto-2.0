package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;

@Data
@ApiModel("csdn私信DTO")
public class CsdnHistorySessionDTO extends Model<CsdnHistorySessionDTO> {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("CSDN用户名称")
    private String userName;
    @ApiModelProperty("CSDN用户昵称")
    private String nickName;
    @ApiModelProperty("回复内容")
    private String content;
    @ApiModelProperty("私信地址")
    private String messageUrl;
    @ApiModelProperty("0未回复,1已回复")
    private Integer hasReplied;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnHistorySessionDTO, CsdnHistorySession> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}