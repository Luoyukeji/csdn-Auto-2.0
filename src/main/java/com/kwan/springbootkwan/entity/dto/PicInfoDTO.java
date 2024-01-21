package com.kwan.springbootkwan.entity.dto;

import com.kwan.springbootkwan.entity.PicInfo;
import com.kwan.springbootkwan.mapstruct.FromConverter;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.Date;


@Data
public class PicInfoDTO {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("图片名称")
    private String picName;
    @ApiModelProperty("图片地址")
    private String picUrl;
    @ApiModelProperty("图片类型,0:表示宝宝图片,1:学习图片,2:风景,3:美女,99:其他")
    private String type;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("删除标识")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<PicInfoDTO, PicInfo> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}