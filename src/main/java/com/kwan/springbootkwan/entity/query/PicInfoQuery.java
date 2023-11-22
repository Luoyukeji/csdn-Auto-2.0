package com.kwan.springbootkwan.entity.query;

import com.kwan.springbootkwan.entity.BasePage;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("csdn三连用户查询参数类")
public class PicInfoQuery extends BasePage {
    private Integer id;
    //图片名称
    private String picName;
    //图片地址
    private String picUrl;
    //图片类型,0:表示宝宝图片,1:学习图片,2:风景,3:美女,99:其他
    private Integer type;
    private Date createTime;
    private Integer isDelete;
}