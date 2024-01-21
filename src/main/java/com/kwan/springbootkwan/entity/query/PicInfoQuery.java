package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn三连用户查询参数类")
public class PicInfoQuery extends BasePage {
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
}