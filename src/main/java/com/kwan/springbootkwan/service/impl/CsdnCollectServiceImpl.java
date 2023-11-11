package com.kwan.springbootkwan.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.CollectInfoQuery;
import com.kwan.springbootkwan.entity.csdn.CollectResponse;
import com.kwan.springbootkwan.entity.csdn.IsCollectResponse;
import com.kwan.springbootkwan.enums.CollectStatus;
import com.kwan.springbootkwan.service.CsdnCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsdnCollectServiceImpl implements CsdnCollectService {

    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.self_folder_id}")
    private Integer selfFolderId;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.url.is_collect_url}")
    private String isCollectUrl;
    @Value("${csdn.url.add_collect_url}")
    private String addCollectUrl;

    @Override
    public Boolean isCollect(String articleId, CsdnUserInfo csdnUserInfo) {
        HttpResponse response = HttpUtil.createGet(isCollectUrl)
                .header("Cookie", csdnCookie)
                .form("articleId", articleId)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final int code = objectMapper.readValue(body, IsCollectResponse.class).code;
            final IsCollectResponse.CollectDataDetail data = objectMapper.readValue(body, IsCollectResponse.class).getData();
            if (code == 200) {
                final boolean status = data.status;
                if (status) {
                    log.info("文章{}已经收藏过", articleId);
                    csdnUserInfo.setCollectStatus(CollectStatus.HAVE_ALREADY_COLLECT.getCode());
                } else {
                    log.info("文章{}未收藏", articleId);
                    csdnUserInfo.setCollectStatus(CollectStatus.UN_PROCESSED.getCode());
                }
                return status;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Boolean collect(BusinessInfoResponse.ArticleData.Article article, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo) {
        final String userName = csdnUserInfo.getUserName();
        final Integer collectStatus = csdnUserInfo.getCollectStatus();
        if (CollectStatus.HAVE_ALREADY_COLLECT.getCode().equals(collectStatus) || CollectStatus.COLLECT_IS_FULL.getCode().equals(collectStatus)) {
            return true;
        }
        final String urlInfo = article.getUrl();
        String articleId = urlInfo.substring(urlInfo.lastIndexOf("/") + 1);
        CollectResponse collectResponse = null;
        try {
            CollectInfoQuery collectInfoQuery = new CollectInfoQuery();
            collectInfoQuery.setSourceId(Integer.valueOf(articleId));
            collectInfoQuery.setFromType("PC");
            collectInfoQuery.setAuthor(userName);
            collectInfoQuery.setDescription(article.getDescription());
            collectInfoQuery.setSource("blog");
            List<Integer> list = new ArrayList<>();
            list.add(selfFolderId);
            collectInfoQuery.setFolderIdList(list);
            collectInfoQuery.setTitle(article.getTitle());
            collectInfoQuery.setUrl(article.getUrl());
            collectInfoQuery.setUsername(selfUserName);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonCollectInfo = objectMapper.writeValueAsString(collectInfoQuery);
            HttpResponse response = HttpUtil.createPost(addCollectUrl)
                    .header("Cookie", csdnCookie)
                    .header("Content-Type", "application/json")
                    .body(jsonCollectInfo)
                    .execute();
            final String body = response.body();
            collectResponse = objectMapper.readValue(body, CollectResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        final int code = collectResponse.code;
        if (code == 200) {
            log.info("文章{}收藏成功", articleId);
            csdnUserInfo.setCollectStatus(CollectStatus.COLLECT_SUCCESSFUL.getCode());
            csdnTripletDayInfo.setCollectNum(csdnTripletDayInfo.getCollectNum() + 1);
            csdnTripletDayInfo.setCollectStatus(CollectStatus.COLLECT_SUCCESSFUL.getCode());
        } else if (code == 400000101) {
            log.info("收藏文章{}参数缺失", articleId);
            csdnUserInfo.setCollectStatus(CollectStatus.MISSING_PARAMETER.getCode());
            csdnTripletDayInfo.setCollectStatus(CollectStatus.MISSING_PARAMETER.getCode());
        } else if (code == 400) {
            log.info("今日收藏次数已达上限!");
            csdnUserInfo.setCollectStatus(CollectStatus.COLLECT_IS_FULL.getCode());
            csdnTripletDayInfo.setCollectStatus(CollectStatus.COLLECT_IS_FULL.getCode());
        } else {
            log.info("其他收藏错误");
            csdnUserInfo.setCollectStatus(CollectStatus.OTHER_ERRORS.getCode());
            csdnTripletDayInfo.setCollectStatus(CollectStatus.OTHER_ERRORS.getCode());
        }
        return true;
    }
}