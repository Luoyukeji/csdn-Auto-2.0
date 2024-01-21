package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("算法题实体类")
@TableName("algorithmic_problem")
public class AlgorithmicProblem extends Model<AlgorithmicProblem> {
    @ApiModelProperty("主键id")
    private Integer id;
    @ApiModelProperty("问题名称")
    private String questionName;
    @ApiModelProperty("问题类型")
    private String questionType;
    @ApiModelProperty("1~10的分值")
    private Integer degreeOfImportance;
    @ApiModelProperty("1:简单;2:中等;3:困难")
    private Integer degreeOfDifficulty;
    @ApiModelProperty("困难指数")
    private Integer difficultyOfScore;
    @ApiModelProperty("力扣的问题号")
    private Integer leetcodeNumber;
    @ApiModelProperty("力扣的问题链接")
    private String leetcodeLink;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("逻辑删除,0未删除,1已删除")
    private Integer isDelete;
}