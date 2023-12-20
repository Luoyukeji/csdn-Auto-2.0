package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("chatgpt问答实体类")
public class Chatbot extends Model<Chatbot> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("问题")
    private String question;
    @ApiModelProperty("回答")
    private String response;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("删除标识")
    private Integer isDelete;
}