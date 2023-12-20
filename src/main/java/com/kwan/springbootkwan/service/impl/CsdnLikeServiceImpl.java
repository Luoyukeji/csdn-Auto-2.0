package com.kwan.springbootkwan.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.LikeResponse;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.service.CsdnLikeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class CsdnLikeServiceImpl implements CsdnLikeService {

    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.url.like_url}")
    private String url;

    @Override
    public Boolean isLike(String articleId, CsdnUserInfo csdnUserInfo) {
        final String userName = csdnUserInfo.getUserName();
        String url = "https://blog.csdn.net/" + userName + "/article/details/" + articleId;
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("articleId", articleId)
                .execute();
        final String body = response.body();
        if (body.contains("isLikeStatus = true")) {
            log.info("文章{}已经点过赞", articleId);
            return true;
        } else {
            log.info("文章{}未点过赞", articleId);
            return false;
        }
    }

    @Override
    public Boolean like(String articleId, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo) {
        HttpResponse response = HttpUtil.createPost(url)
                .header("Cookie", csdnCookie)
                .form("articleId", articleId)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final LikeResponse likeResponse = objectMapper.readValue(body, LikeResponse.class);
            final int code = likeResponse.code;
            final String message = likeResponse.getMessage();
            final LikeResponse.LikeDataDetail data = likeResponse.getData();
            if (code == 200) {
                final boolean status = data.status;
                if (status) {
                    log.info("文章{}点赞成功", articleId);
                    csdnUserInfo.setLikeStatus(LikeStatus.LIKE_SUCCESSFUL.getCode());
                    csdnTripletDayInfo.setLikeStatus(LikeStatus.LIKE_SUCCESSFUL.getCode());
                    csdnTripletDayInfo.setLikeNum(csdnTripletDayInfo.getLikeNum() + 1);
                } else {
                    log.info("文章{}点赞取消", articleId);
                    csdnUserInfo.setLikeStatus(LikeStatus.CANCEL_LIKES.getCode());
                }
                return status;
            } else if (code == 400 && StringUtils.equals(message, "文章状态不能点赞")) {
                log.info("文章状态不能点赞!");
                csdnUserInfo.setLikeStatus(LikeStatus.CAN_NOT_LIKES.getCode());
            } else if (code == 400 && StringUtils.equals(message, "今日点赞次数已达上限!")) {
                log.info("今日点赞次数已达上限!");
                csdnUserInfo.setLikeStatus(LikeStatus.LIKE_IS_FULL.getCode());
                csdnTripletDayInfo.setLikeStatus(LikeStatus.LIKE_IS_FULL.getCode());
            } else {
                log.info("code={}", code);
                log.info("message={}", message);
                csdnUserInfo.setLikeStatus(LikeStatus.OTHER_ERRORS.getCode());
                csdnTripletDayInfo.setLikeStatus(LikeStatus.OTHER_ERRORS.getCode());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }
}