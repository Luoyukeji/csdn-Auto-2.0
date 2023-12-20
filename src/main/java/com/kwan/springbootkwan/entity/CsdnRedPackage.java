package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@ApiModel("csdn红包管理")
@TableName("csdn_red_package")
public class CsdnRedPackage extends Model<CsdnRedPackage> {
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
    @ApiModelProperty("红包订单号")
    private String orderNo;
    @ApiModelProperty("浏览量")
    private Integer viewCount;
    @ApiModelProperty("我抢到的金额")
    private BigDecimal myAmount;

    @ApiModelProperty("红包总金额")
    private BigDecimal totalAmount;
    @ApiModelProperty("分享链接")
    private String shareUrl;
    @ApiModelProperty("红包情况")
    private String msg;
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;
    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;

    public String dateInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(createTime);
    }
}