package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 字典表(DictionaryManagement)表实体类
 *
 * @author makejava
 * @since 2023-10-07 17:54:39
 */
@Data
public class DictionaryManagement extends Model<DictionaryManagement> {
    //主键id
    private Integer id;
    //字典类型
    private Integer dictType;
    //字典code
    private Integer code;
    //字典名称
    private String name;
    //创建时间
    private Date createTime;
    //逻辑删除,0未删除,1已删除
    private Integer isDelete;
}