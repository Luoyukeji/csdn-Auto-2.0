package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 面试题(InterviewQuestion)表实体类
 *
 * @author makejava
 * @since 2023-09-08 16:31:53
 */
@Data
@SuppressWarnings("serial")
public class InterviewQuestion extends Model<InterviewQuestion> {
    //主键id
    private Integer id;
    //面试问题
    private String question;
    //问题回答
    private String response;
    //知识类型,先默认0,后面再区分
    private Integer questionType;
    //创建时间
    private Date createTime;
    //逻辑删除,0未删除,1已删除
    private Integer isDelete;
}

