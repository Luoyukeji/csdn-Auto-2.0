package com.kwan.springbootkwan.service;


/**
 * 自动回复
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2024/1/22 16:32
 */
public interface CsdnAutoReplyService {

    /**
     * 评论自己的文章
     *
     * @return
     */
    void commentSelf();
}