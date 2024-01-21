package com.kwan.springbootkwan.service;

import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CommentResponse;


public interface CsdnArticleCommentService {

    /**
     * 查询是否评论过
     *
     * @return
     */
    Boolean isComment(CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo);

    /**
     * 评论别人的文章
     *
     * @return
     */
    Boolean comment(String articleId, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo);

    /**
     * 评论文章
     *
     * @param articleId   文章id
     * @param commentInfo 评论信息
     * @param commentId   评论id
     * @return
     */
    CommentResponse dealComment(String articleId, String commentInfo, Integer commentId);
}