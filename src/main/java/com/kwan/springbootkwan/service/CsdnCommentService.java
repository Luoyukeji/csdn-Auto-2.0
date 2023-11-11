package com.kwan.springbootkwan.service;

import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.CommentResponse;

/**
 * 评论
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/10/24 01:25
 */
public interface CsdnCommentService {

    /**
     * 查询是否评论过
     *
     * @return
     */
    Boolean isComment(BusinessInfoResponse.ArticleData.Article article, CsdnUserInfo csdnUserInfo);

    /**
     * 评论别人的文章
     *
     * @return
     */
    Boolean comment(String articleId, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo);

    /**
     * 评论文章
     *
     * @param articleId
     * @param commentInfo
     * @param commentId
     * @return
     */
    CommentResponse dealComment(String articleId, String commentInfo, Integer commentId);
}