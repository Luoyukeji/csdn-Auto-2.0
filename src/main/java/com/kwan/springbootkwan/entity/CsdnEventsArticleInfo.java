package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;


@Data
@SuppressWarnings("serial")
public class CsdnEventsArticleInfo extends Model<CsdnEventsArticleInfo> {
    //主键id
    private Integer id;
    //标题
    private String title;
    //描述
    private String descInfo;
    //用户昵称
    private String authorNickName;
    //浏览量
    private Integer viewCount;
    //评论数
    private Integer commentCount;
    //文章id
    private Integer articleId;
    //文章url
    private String url;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //逻辑删除,0未删除,1已删除
    private Integer isDelete;
}

