package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.MessageHistoryListResponse;
import com.kwan.springbootkwan.entity.csdn.MessageResponse;
import com.kwan.springbootkwan.mapper.CsdnHistorySessionMapper;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnMessageServiceImpl extends ServiceImpl<CsdnHistorySessionMapper, CsdnHistorySession> implements CsdnMessageService {
    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnFollowFansInfoService csdnFollowFansInfoService;

    @Override
    public List<CsdnUserInfo> acquireMessage() {
        List<CsdnUserInfo> csdnUserInfos = new ArrayList<>();
        String url = "https://msg.csdn.net/v1/im/query/app/historySession";
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("page", 1)
                .form("pageSize", 100)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        MessageResponse businessInfoResponse;
        List<MessageResponse.MessageData.Sessions> sessions = null;
        try {
            businessInfoResponse = objectMapper.readValue(body, MessageResponse.class);
            sessions = businessInfoResponse.getData().getSessions();
            if (CollectionUtil.isNotEmpty(sessions)) {
                // 反向遍历主要是为了和展示一致
                for (int i = sessions.size() - 1; i >= 0; i--) {
                    final MessageResponse.MessageData.Sessions session = sessions.get(i);
                    final String userName = session.getUsername();
                    final String nickName = session.getNickname();
                    final String content = session.getContent();
                    final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                    if (Objects.nonNull(articleInfo)) {
                        CsdnUserInfo userInfo = csdnUserInfoService.getUserInfo(userName, nickName);
                        final Boolean aBoolean = this.haveRepliedMessage(userName, articleInfo.getArticleUrl());
                        CsdnHistorySession csdnHistorySession = this.getCsdnHistorySession(userName);
                        if (Objects.isNull(csdnHistorySession)) {
                            csdnHistorySession = new CsdnHistorySession();
                            csdnHistorySession.setUserName(userName);
                            csdnHistorySession.setNickName(nickName);
                            csdnHistorySession.setContent(content);
                            csdnHistorySession.setHasReplied(aBoolean ? 1 : 0);
                            csdnHistorySession.setMessageUrl("https://i.csdn.net/#/msg/chat/" + userName);
                            this.save(csdnHistorySession);
                        } else {
                            final String contentHistory = csdnHistorySession.getContent();
                            if (!StringUtils.equals(content, contentHistory)) {
                                csdnHistorySession.setContent(content);
                                csdnHistorySession.setHasReplied(aBoolean ? 1 : 0);
                                this.updateById(csdnHistorySession);
                            }
                        }
                        csdnUserInfos.add(userInfo);

                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return csdnUserInfos;
    }

    @Override
    public void sendMessage(CsdnArticleInfo csdnArticleInfo) {
        final String userName = csdnArticleInfo.getUserName();
        final CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserCsdnApi(userName);
        if (Objects.nonNull(csdnUserInfo)) {
            this.dealMessageByUserName(csdnArticleInfo);
        }
    }

    @Override
    public void dealMessageByUserName(CsdnArticleInfo csdnArticleInfo) {
        final String userName = csdnArticleInfo.getUserName();
        final String nickname = csdnArticleInfo.getNickName();
        final String url = csdnArticleInfo.getArticleUrl();
        if (!this.haveRepliedMessage(userName, url)) {
            CsdnHistorySession csdnHistorySession = this.getCsdnHistorySession(userName);
            if (csdnFollowFansInfoService.isIntercorrelation(userName)) {
                final String title = csdnArticleInfo.getArticleTitle();
                LocalTime currentTime = LocalTime.now();
                String timeStr;
                if (currentTime.isBefore(LocalTime.NOON)) {
                    timeStr = "上午好☀️☀️☀️️";
                } else if (currentTime.isBefore(LocalTime.of(13, 0))) {
                    timeStr = "中午好\uD83C\uDF1E\uD83C\uDF1E\uD83C\uDF1E";
                } else if (currentTime.isBefore(LocalTime.of(18, 0))) {
                    timeStr = "下午好\uD83C\uDF05\uD83C\uDF05\uD83C\uDF05";
                } else {
                    timeStr = "晚上好\uD83C\uDF19\uD83C\uDF19\uD83C\uDF19";
                }
                String messageBody = nickname + "大佬" + timeStr + "\n" +
                        "恭喜大佬发布佳作\uD83C\uDF89\uD83C\uDF89\uD83C\uDF89\n" +
                        "✨" + title + "✨\n" +
                        "\uD83D\uDD25 " + url + "\n" +
                        "已一键三连，欢迎大佬回访。☕☕☕";
                this.replyMessage(userName, 0, messageBody, "WEB", "10_20285116700–1699412958190–182091", "CSDN-PC");
                if (Objects.nonNull(csdnHistorySession)) {
                    csdnHistorySession.setHasReplied(1);
                    this.updateById(csdnHistorySession);
                }
                this.messageRead(userName);
            }
        }
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
    public Boolean haveRepliedMessage(String userName, String blogUrl) {
        String url = "https://msg.csdn.net/v1/im/query/history";
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("fromUsername", userName)
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
                    if (StringUtils.equalsIgnoreCase(fromUsernameHistory, selfUserName) && messageBody.contains(blogUrl)) {
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