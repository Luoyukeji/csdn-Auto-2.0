package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel("字典表")
public class DictionaryManagement extends Model<DictionaryManagement> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("字典类型")
    private Integer dictType;
    @ApiModelProperty("字典code")
    private Integer code;
    @ApiModelProperty("字典名称")
    private String name;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}