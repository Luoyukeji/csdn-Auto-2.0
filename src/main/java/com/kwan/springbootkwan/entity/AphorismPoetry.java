package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("名言警句实体类")
public class AphorismPoetry extends Model<AphorismPoetry> {

    @ApiModelProperty("主键id")
    private Integer id;

    @ApiModelProperty("诗词内容")
    private String poetryText;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

