package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 图片信息表(PicInfo)表实体类
 *
 * @author makejava
 * @since 2023-08-09 12:44:03
 */
@Data
@SuppressWarnings("serial")
public class PicInfo extends Model<PicInfo> {
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