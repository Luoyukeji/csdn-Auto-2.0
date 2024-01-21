package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CommentListResponse;
import com.kwan.springbootkwan.entity.csdn.CommentResponse;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.service.CsdnArticleCommentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnArticleCommentServiceImpl implements CsdnArticleCommentService {

    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("#{'${csdn.self_comment}'.split(';')}")
    private String[] selfComment;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.url.is_comment_list_url}")
    private String commentListUrl;
    @Value("${csdn.url.comment_url}")
    private String commentUrl;


    @Override
    public Boolean isComment(CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo) {
        final String articleId = csdnArticleInfo.getArticleId();
        String url = commentListUrl + articleId;
        HttpResponse response = HttpUtil.createPost(url)
                .header("Cookie", csdnCookie)
                .form("page", 1)
                .form("size", 200)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        CommentListResponse articleInfo;
        try {
            articleInfo = objectMapper.readValue(body, CommentListResponse.class);
            final CommentListResponse.DataInfo data = articleInfo.getData();
            final List<CommentListResponse.Comment> list = data.getList();
            if (CollectionUtil.isNotEmpty(list)) {
                for (CommentListResponse.Comment comment : list) {
                    final CommentListResponse.Info info = comment.getInfo();
                    final String userName = info.getUserName();
                    if (StringUtils.equalsIgnoreCase(userName, selfUserName)) {
                        log.info("文章{}已经评论过", articleId);
                        csdnUserInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
                        return true;
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        csdnUserInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
        return false;
    }

    @Override
    public Boolean comment(String articleId, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo) {
        int start = -1;
        int end = selfComment.length;
        int temp_count = (int) (Math.floor(Math.random() * (start - end + 1)) + end);
        CommentResponse comment = this.dealComment(articleId, selfComment[temp_count], null);
        final int code = comment.code;
        final String message = comment.getMessage();
        if (code == 200) {
            log.info("文章{}评论成功", articleId);
            csdnUserInfo.setCommentStatus(CommentStatus.COMMENT_SUCCESSFUL.getCode());
            csdnTripletDayInfo.setCommentNum(csdnTripletDayInfo.getCommentNum() + 1);
        } else if (code == 400 && StringUtils.equals(message, "您已达到当日发送上限，请明天尝试！")) {
            log.info(message);
            csdnUserInfo.setCommentStatus(CommentStatus.COMMENT_IS_FULL.getCode());
            csdnTripletDayInfo.setCommentStatus(CommentStatus.COMMENT_IS_FULL.getCode());
        } else if (code == 400 && message.contains("因存在恶意评论嫌疑，您的账号已被禁言")) {
            log.info("因存在恶意评论嫌疑，您的账号已被禁言");
            csdnUserInfo.setCommentStatus(CommentStatus.RESTRICTED_COMMENTS.getCode());
            csdnTripletDayInfo.setCommentStatus(CommentStatus.RESTRICTED_COMMENTS.getCode());
        } else if (code == 400 && StringUtils.equals(message, "您评论太快了，请休息一下！")) {
            log.info("您评论太快了，请休息一下！");
            csdnUserInfo.setCommentStatus(CommentStatus.COMMENT_TOO_FAST.getCode());
        } else {
            log.info("其他错误");
            csdnUserInfo.setCommentStatus(CommentStatus.OTHER_ERRORS.getCode());
        }
        return true;
    }

    /**
     * 回复评论
     *
     * @param articleId
     * @return
     */
    @Override
    public CommentResponse dealComment(String articleId, String commentInfo, Integer commentId) {
        HttpResponse response;
        CommentResponse commentResponse = null;
        try {
            if (Objects.nonNull(commentId)) {
                response = HttpUtil.createPost(commentUrl)
                        .header("Cookie", csdnCookie)
                        .form("articleId", articleId)
                        .form("content", commentInfo)
                        .form("commentId", commentId)
                        .execute();
            } else {
                response = HttpUtil.createPost(commentUrl)
                        .header("Cookie", csdnCookie)
                        .form("articleId", articleId)
                        .form("content", commentInfo)
                        .execute();
            }

            final String body = response.body();
            log.info(body);
            ObjectMapper objectMapper = new ObjectMapper();
            commentResponse = objectMapper.readValue(body, CommentResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return commentResponse;
    }
}