package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("面试题实体类")
public class InterviewQuestion extends Model<InterviewQuestion> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("面试问题")
    private String question;
    @ApiModelProperty("问题回答")
    private String response;
    @ApiModelProperty("知识类型")
    private String questionType;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}

