package com.kwan.springbootkwan.entity.dto;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.kwan.springbootkwan.entity.CsdnRedPackage;
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
@ApiModel("csdn红包DTO")
public class CsdnRedPackageDTO extends Model<CsdnRedPackageDTO> {

    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("内容id")
    private String contentId;
    @ApiModelProperty("社区id")
    private Integer communityId;
    @ApiModelProperty("发布id")
    private Integer postId;
    @ApiModelProperty("用户名称")
    private String userName;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("红包出处")
    private String itemType;
    @ApiModelProperty("主题名称")
    private String topicTitle;
    @ApiModelProperty("分享链接")
    private String shareUrl;
    @ApiModelProperty("红包订单号")
    private String orderNo;
    @ApiModelProperty("浏览量")
    private Integer viewCount;
    @ApiModelProperty("我抢到的金额")
    private BigDecimal myAmount;
    @ApiModelProperty("红包总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("红包情况")
    private String msg;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    @Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    public interface Converter extends FromConverter<CsdnRedPackageDTO, CsdnRedPackage> {
        Converter INSTANCE = Mappers.getMapper(Converter.class);
    }
}