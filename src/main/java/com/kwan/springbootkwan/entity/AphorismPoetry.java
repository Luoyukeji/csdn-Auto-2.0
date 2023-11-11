package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 名言警句(AphorismPoetry)表实体类
 *
 * @author makejava
 * @since 2023-10-09 11:13:13
 */
@SuppressWarnings("serial")
@Data
public class AphorismPoetry extends Model<AphorismPoetry> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 诗词内容
     */
    private String poetryText;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 逻辑删除,0未删除,1已删除
     */
    private Integer isDelete;
}

