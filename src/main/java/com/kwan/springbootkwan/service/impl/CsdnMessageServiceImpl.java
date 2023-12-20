package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.MessageHistoryListResponse;
import com.kwan.springbootkwan.entity.csdn.MessageResponse;
import com.kwan.springbootkwan.mapper.CsdnHistorySessionMapper;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
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
import java.util.stream.Collectors;


@Slf4j
@Service
public class CsdnMessageServiceImpl extends ServiceImpl<CsdnHistorySessionMapper, CsdnHistorySession> implements CsdnMessageService {

    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.self_user_name}")
    private String selfUserName;

    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnFollowFansInfoService csdnFollowFansInfoService;


    @Override
    public List<MessageResponse.MessageData.Sessions> acquireMessage() {
        String url = "https://msg.csdn.net/v1/im/query/app/historySession";
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("page", 1)
                .form("pageSize", 100)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageResponse businessInfoResponse;
        try {
            businessInfoResponse = objectMapper.readValue(body, MessageResponse.class);
            final List<MessageResponse.MessageData.Sessions> sessions = businessInfoResponse.getData().getSessions();
            if (CollectionUtil.isNotEmpty(sessions)) {
                //反向遍历主要是为了和展示一致
                for (int i = sessions.size() - 1; i >= 0; i--) {
                    final MessageResponse.MessageData.Sessions session = sessions.get(i);
                    final String username = session.getUsername();
                    final String content = session.getContent();
                    CsdnHistorySession csdnHistorySession = this.getCsdnHistorySession(username);
                    List<BusinessInfoResponse.ArticleData.Article> blogs10 = csdnArticleInfoService.getArticles10(username);
                    if (CollectionUtil.isNotEmpty(blogs10)) {
                        blogs10 = blogs10.stream().filter(x -> x.getType().equals(CommonConstant.ARTICLE_TYPE_BLOG)).collect(Collectors.toList());
                        if (CollectionUtil.isNotEmpty(blogs10)) {
                            final BusinessInfoResponse.ArticleData.Article article = blogs10.get(0);
                            final String blogUrl = article.getUrl();
                            final Boolean aBoolean = this.haveRepliedMessage(username, blogUrl);
                            if (Objects.isNull(csdnHistorySession)) {
                                csdnHistorySession = new CsdnHistorySession();
                                csdnHistorySession.setUserName(username);
                                csdnHistorySession.setNickName(session.getNickname());
                                csdnHistorySession.setContent(content);
                                csdnHistorySession.setHasReplied(aBoolean ? 1 : 0);
                                csdnHistorySession.setMessageUrl("https://i.csdn.net/#/msg/chat/" + username);
                                this.save(csdnHistorySession);
                            } else {
                                final String contentHistory = csdnHistorySession.getContent();
                                if (!StringUtils.equals(content, contentHistory)) {
                                    csdnHistorySession.setContent(content);
                                    csdnHistorySession.setHasReplied(aBoolean ? 1 : 0);
                                    this.updateById(csdnHistorySession);
                                }
                            }

                        }
                    }
                }
            }
            return sessions;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void dealMessage(List<MessageResponse.MessageData.Sessions> acquireMessage) {
        if (CollectionUtil.isNotEmpty(acquireMessage)) {
            for (MessageResponse.MessageData.Sessions sessions : acquireMessage) {
                this.dealMessageByUserName(sessions.getUsername());
            }
        }
    }

    @Override
    public void dealMessageByUserName(String username) {
        List<BusinessInfoResponse.ArticleData.Article> blogs10 = csdnArticleInfoService.getArticles10(username);
        if (CollectionUtil.isNotEmpty(blogs10)) {
            blogs10 = blogs10.stream().filter(x -> x.getType().equals(CommonConstant.ARTICLE_TYPE_BLOG)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(blogs10)) {
                final BusinessInfoResponse.ArticleData.Article article = blogs10.get(0);
                final String url = article.getUrl();
                if (!this.haveRepliedMessage(username, url)) {
                    CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
                    CsdnHistorySession csdnHistorySession = this.getCsdnHistorySession(username);
                    if (Objects.isNull(csdnUserInfo) && Objects.nonNull(csdnHistorySession)) {
                        csdnUserInfo = new CsdnUserInfo();
                        csdnUserInfo.setUserName(username);
                        csdnUserInfo.setNickName(csdnHistorySession.getNickName());
                        csdnUserInfo.setLikeStatus(0);
                        csdnUserInfo.setCollectStatus(0);
                        csdnUserInfo.setCommentStatus(0);
                        csdnUserInfo.setUserWeight(7);
                        csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + username);
                        csdnUserInfoService.save(csdnUserInfo);
                    }
                    final CsdnArticleInfo csdnArticleInfo = this.buildCsdnArticleInfo(article, csdnUserInfo);
                    csdnService.tripletByArticle(csdnUserInfo, article, csdnArticleInfo);
                    final String nickname = csdnUserInfo.getNickName();
                    if (csdnFollowFansInfoService.isIntercorrelation(username)) {
                        final String title = article.getTitle();
                        String messageBody = "恭喜" + nickname + "大佬发布佳作\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
                                "✨" + title + "✨\n" +
                                "\uD83D\uDD25 " + url + "\n" +
                                "已一键三连，欢迎大佬回访。☕☕☕";
                        this.replyMessage(username, 0, messageBody, "WEB", "10_20285116700–1699412958190–182091", "CSDN-PC");
                        if (Objects.nonNull(csdnHistorySession)) {
                            csdnHistorySession.setHasReplied(1);
                            this.updateById(csdnHistorySession);
                        }
                        this.messageRead(username);
                    }
                }
            }
        }
    }

    /**
     * 获取文章的信息
     *
     * @param article
     * @param csdnUserInfo
     */
    private CsdnArticleInfo buildCsdnArticleInfo(BusinessInfoResponse.ArticleData.Article article, CsdnUserInfo csdnUserInfo) {
        String username = csdnUserInfo.getUserName();
        final String articleUrl = article.getUrl();
        String articleIdFormUrl = articleUrl.substring(articleUrl.lastIndexOf("/") + 1);
        final Object articleId = article.getArticleId();
        article.setArticleId(Objects.isNull(articleId) ? articleIdFormUrl : articleId.toString());
        CsdnArticleInfo csdnArticleInfo = this.csdnArticleInfoService.getArticleByArticleId(article.getArticleId());
        if (Objects.isNull(csdnArticleInfo)) {
            csdnArticleInfo = new CsdnArticleInfo();
            csdnArticleInfo.setArticleId(article.getArticleId());
            csdnArticleInfo.setArticleUrl(articleUrl);
            csdnArticleInfo.setArticleTitle(article.getTitle());
            csdnArticleInfo.setArticleDescription(article.getDescription());
            csdnArticleInfo.setUserName(username);
            csdnArticleInfo.setNickName(csdnUserInfo.getNickName());
            final Integer score = csdnArticleInfoService.getScore(articleUrl);
            log.info("质量分={}", score);
            csdnArticleInfo.setArticleScore(score);
            if (StringUtils.equals(selfUserName, username)) {
                csdnArticleInfo.setIsMyself(1);
            }
            this.csdnArticleInfoService.saveArticle(csdnArticleInfo);
        }
        return csdnArticleInfo;
    }


    @Override
    public CsdnHistorySession getCsdnHistorySession(String username) {
        QueryWrapper<CsdnHistorySession> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("user_name", username);
        return this.getOne(wrapper);
    }

    @Override
    public void sendRedPacketNotice(String userName, String nickName, String shareUrl) {
        if (!haveRepliedMessage(userName, shareUrl)) {
            String messageBody = "抢红包啦!\uD83E\uDDE7\uD83E\uDDE7"
                    + "抢红包啦!\uD83E\uDDE7\uD83E\uDDE7"
                    + "\n"
                    + "为回馈粉丝,现推出抢红包通知功能,点击链接在评论区即可领取红包\n"
                    + shareUrl
                    + "\n温馨提示,手速要快,红包稍纵即逝!"
                    + "\n如果不需要此通知,请回复[不需要]谢谢各位大佬!";
            this.replyMessage(userName, 0, messageBody, "WEB", "10_20285116700–1699412958190–182091", "CSDN-PC");
        }
    }


    @Override
    public void replyMessage(String toUsername, Integer messageType, String messageBody, String fromClientType
            , String fromDeviceId, String appId) {
        final String url = "https://msg.csdn.net/v1/im/send/message";
        HttpResponse response = HttpUtil.createPost(url)
                .header("Cookie", csdnCookie)
                .form("toUsername", toUsername)
                .form("messageType", messageType)
                .form("messageBody", messageBody)
                .form("fromClientType", fromClientType)
                .form("fromDeviceId", fromDeviceId)
                .form("appId", appId)
                .execute();
        final int status = response.getStatus();
        log.info("replyMessage() status={}", status);
    }

    @Override
    public void messageRead(String toUsername) {
        String url = "https://msg.csdn.net/v1/im/session/settings";
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("toUsername", toUsername)
                .execute();
        log.info("messageRead response status={}", response.getStatus());
    }

    @Override
    public Boolean haveRepliedMessage(String fromUsername, String blogUrl) {
        String url = "https://msg.csdn.net/v1/im/query/history";
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("fromUsername", fromUsername)
                .form("limit", 1000)
                .form("time", System.currentTimeMillis())
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageHistoryListResponse messageHistoryListResponse;
        try {
            messageHistoryListResponse = objectMapper.readValue(body, MessageHistoryListResponse.class);
            final List<MessageHistoryListResponse.MessageHistoryListData> data = messageHistoryListResponse.getData();
            if (CollectionUtil.isNotEmpty(data)) {
                for (MessageHistoryListResponse.MessageHistoryListData datum : data) {
                    final String fromUsernameHistory = datum.getFromUsername();
                    final String messageBody = datum.getMessageBody();
                    //本人的回复
                    if (StringUtils.equals(fromUsernameHistory, selfUserName) && messageBody.contains(blogUrl)) {
                        return true;
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }
}