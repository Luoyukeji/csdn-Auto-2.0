package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.CommentListResponse;
import com.kwan.springbootkwan.entity.csdn.CommentResponse;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnCommentService;
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
    @Value("${csdn.url.is_comment_list_url}")
    private String commentListUrl;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnCommentService csdnCommentService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;

    @Override
    public void commentSelf() {
        List<BusinessInfoResponse.ArticleData.Article> list = csdnArticleInfoService.getArticles10(selfUserName);
        if (CollectionUtil.isNotEmpty(list)) {
            for (BusinessInfoResponse.ArticleData.Article article : list) {
                final String type = article.getType();
                //只处理博客类型
                if (StringUtils.equals(CommonConstant.ARTICLE_TYPE_BLOG, type)) {
                    String articleId = article.getUrl().substring(article.getUrl().lastIndexOf("/") + 1);
                    String url = commentListUrl + articleId;
                    //查找200条评论
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
                        final List<CommentListResponse.Comment> otherCommentList = data.getList();
                        if (CollectionUtil.isNotEmpty(otherCommentList)) {
                            for (CommentListResponse.Comment oneComment : otherCommentList) {
                                final CommentListResponse.Info info = oneComment.getInfo();
                                final String userName = info.getUserName();
                                final String nickName = info.getNickName();
                                final Integer commentId = info.getCommentId();
                                if (!StringUtils.equals(userName, selfUserName)) {
                                    final List<CommentListResponse.SubComment> sub = oneComment.getSub();
                                    //默认自己没有回复评论
                                    boolean flag = false;
                                    if (CollectionUtil.isNotEmpty(sub)) {
                                        for (CommentListResponse.SubComment subComment : sub) {
                                            final String subUserName = subComment.getUserName();
                                            if (StringUtils.equals(subUserName, selfUserName)) {
                                                flag = true;
                                            }
                                        }
                                    }
                                    if (CollectionUtil.isEmpty(sub) || !flag) {
                                        int start = -1;
                                        int end = selfReply.length;
                                        int temp_count = (int) (Math.floor(Math.random() * (start - end + 1)) + end);
                                        CommentResponse reply = csdnCommentService.dealComment(articleId, "感谢" + nickName + "大佬支持！" + selfReply[temp_count], commentId);
                                        log.info(reply.toString());
                                    }
                                    //三连此评论人,不需要回复的时候,也要去三连别人
                                    CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(userName);
                                    if (Objects.isNull(csdnUserInfo)) {
                                        //新增用户
                                        csdnUserInfo = new CsdnUserInfo();
                                        csdnUserInfo.setUserName(userName);
                                        csdnUserInfo.setNickName(nickName);
                                        csdnUserInfo.setLikeStatus(0);
                                        csdnUserInfo.setCollectStatus(0);
                                        csdnUserInfo.setCommentStatus(0);
                                        csdnUserInfo.setUserWeight(7);
                                        csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + userName);
                                        csdnUserInfoService.save(csdnUserInfo);
                                    }
                                    csdnService.singleArticle(csdnUserInfo);
                                }
                            }
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}