package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CommentListResponse;
import com.kwan.springbootkwan.service.CsdnArticleCommentService;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnAutoReplyServiceImpl implements CsdnAutoReplyService {
    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("#{'${csdn.self_reply}'.split(';')}")
    private String[] selfReply;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.self_user_nick_name}")
    private String selfNickName;
    @Value("${csdn.url.is_comment_list_url}")
    private String commentListUrl;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnMessageService csdnMessageService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnArticleCommentService csdnArticleCommentService;

    @Override
    public void commentSelf() {
        final List<CsdnArticleInfo> articles10 = csdnArticleInfoService.getArticles10(selfNickName, selfUserName);
        if (CollectionUtil.isNotEmpty(articles10)) {
            for (CsdnArticleInfo selfArticle : articles10) {
                final String articleId = selfArticle.getArticleId();
                String url = commentListUrl + articleId;
                // 查找200条评论
                HttpResponse response = HttpUtil.createPost(url)
                        .header("Cookie", csdnCookie)
                        .form("page", 1)
                        .form("size", 200)
                        .execute();
                final String body = response.body();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    CommentListResponse articleInfo = objectMapper.readValue(body, CommentListResponse.class);
                    final CommentListResponse.DataInfo data = articleInfo.getData();
                    final List<CommentListResponse.Comment> otherCommentList = data.getList();
                    if (CollectionUtil.isNotEmpty(otherCommentList)) {
                        for (CommentListResponse.Comment oneComment : otherCommentList) {
                            final CommentListResponse.Info info = oneComment.getInfo();
                            final String userName = info.getUserName();
                            final String nickName = info.getNickName();
                            final Integer commentId = info.getCommentId();
                            if (!StringUtils.equalsIgnoreCase(userName, selfUserName)) {
                                final List<CommentListResponse.SubComment> sub = oneComment.getSub();
                                // 默认自己没有回复评论
                                boolean flag = false;
                                if (CollectionUtil.isNotEmpty(sub)) {
                                    for (CommentListResponse.SubComment subComment : sub) {
                                        final String subUserName = subComment.getUserName();
                                        if (StringUtils.equalsIgnoreCase(subUserName, selfUserName)) {
                                            flag = true;
                                        }
                                    }
                                }
                                if (CollectionUtil.isEmpty(sub) || !flag) {
                                    int start = -1;
                                    int end = selfReply.length;
                                    int temp_count = (int) (Math.floor(Math.random() * (start - end + 1)) + end);
                                    final String commentInfo = "感谢" + nickName + "大佬支持！" + selfReply[temp_count];
                                    csdnArticleCommentService.dealComment(articleId, commentInfo, commentId);
                                }
                                final CsdnArticleInfo newArticleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                                if (Objects.nonNull(newArticleInfo)) {
                                    CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserInfo(userName, nickName);
                                    csdnService.tripletByArticle(csdnUserInfo, newArticleInfo);
                                    csdnMessageService.dealMessageByUserName(newArticleInfo);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}