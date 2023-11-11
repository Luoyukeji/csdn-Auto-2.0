package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.MessageHistoryListResponse;
import com.kwan.springbootkwan.entity.csdn.MessageResponse;
import com.kwan.springbootkwan.mapper.CsdnHistorySessionMapper;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
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
                    if (Objects.isNull(csdnHistorySession)) {
                        csdnHistorySession = new CsdnHistorySession();
                        csdnHistorySession.setUserName(username);
                        csdnHistorySession.setNickName(session.getNickname());
                        csdnHistorySession.setContent(content);
                        csdnHistorySession.setHasReplied(session.getHasReplied() ? 1 : 0);
                        csdnHistorySession.setMessageUrl("https://i.csdn.net/#/msg/chat/" + username);
                        this.save(csdnHistorySession);
                    } else {
                        final String contentHistory = csdnHistorySession.getContent();
                        if (!StringUtils.equals(content, contentHistory)) {
                            csdnHistorySession.setContent(content);
                            this.updateById(csdnHistorySession);
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
                dealMessageByUserName(sessions.getUsername());
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
                if (!haveRepliedMessage(username, url)) {
                    //获取最新的一篇文章,进行三连
                    CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
                    if (Objects.nonNull(csdnUserInfo)) {
                        final String nickname = csdnUserInfo.getNickName();
                        csdnService.singleArticle(csdnUserInfo);
                        final String title = article.getTitle();
                        String messageBody = nickname + "大佬最新的文章\n✨" + title + "✨\n" + "\uD83D\uDC4D\uD83C\uDFFB" + url + "\n已三连完成";
                        this.replyMessage(username, 0, messageBody, "WEB", "10_20285116700–1699412958190–182091", "CSDN-PC");
                        CsdnHistorySession csdnHistorySession = this.getCsdnHistorySession(username);
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

    @Override
    public CsdnHistorySession getCsdnHistorySession(String username) {
        QueryWrapper<CsdnHistorySession> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("user_name", username);
        CsdnHistorySession csdnHistorySession = this.getOne(wrapper);
        return csdnHistorySession;
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