package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * (Chatbot)表实体类
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/7/11 18:03
 */
@Data
public class Chatbot extends Model<Chatbot> {
    private Integer id;
    private String question;
    private String response;
    private Date createTime;
    private Integer isDelete;
}