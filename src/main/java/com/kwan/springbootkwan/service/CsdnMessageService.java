package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.entity.CsdnUserInfo;

import java.util.List;

public interface CsdnMessageService extends IService<CsdnHistorySession> {

    /**
     * 查询私信列表
     *
     * @return
     */
    List<CsdnUserInfo> acquireMessage();

    /**
     * 处理私信列表
     *
     * @param acquireMessage
     */
    void sendMessage( CsdnArticleInfo csdnArticleInfo );

    /**
     * 回复私信
     *
     * @param toUsername
     * @param messageType
     * @param messageBody
     * @param fromClientType
     * @param fromDeviceId
     * @param appId
     */
    void replyMessage(String toUsername, Integer messageType, String messageBody, String fromClientType, String fromDeviceId, String appId);

    /**
     * 私信已读
     *
     * @param toUsername
     */
    void messageRead(String toUsername);

    /**
     * 是否回复过私信
     */
    Boolean haveRepliedMessage(String userName, String url);

    /**
     * 根据用户名回复私信
     *
     * @param csdnArticleInfo
     */
    void dealMessageByUserName(CsdnArticleInfo csdnArticleInfo);

    /**
     * 获取私信人信息
     *
     * @param username
     * @return
     */
    CsdnHistorySession getCsdnHistorySession(String username);

    /**
     * 发送红包私信
     *
     * @param userName
     * @param nickName
     * @param shareUrl
     */
    void sendRedPacketNotice(String userName, String nickName, String shareUrl);
}