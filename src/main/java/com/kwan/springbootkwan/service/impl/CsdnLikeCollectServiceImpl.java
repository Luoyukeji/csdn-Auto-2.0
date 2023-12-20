package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.LikeCollectQuery;
import com.kwan.springbootkwan.entity.csdn.LikeCollectResponse;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnLikeCollectServiceImpl implements CsdnLikeCollectService {

    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnMessageService csdnMessageService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnFollowFansInfoService csdnFollowFansInfoService;

    @Override
    public List<LikeCollectResponse.LikeCollectDataDetail.ResultList> acquireLikeCollect() {
        try {
            LikeCollectQuery collectInfoQuery = new LikeCollectQuery();
            collectInfoQuery.setPageIndex(1);
            collectInfoQuery.setPageSize(100);
            collectInfoQuery.setType(2);
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonCollectInfo = objectMapper.writeValueAsString(collectInfoQuery);
            HttpResponse response = HttpUtil.createPost("https://msg.csdn.net/v1/web/message/view/message")
                    .header("Cookie", csdnCookie)
                    .header("Content-Type", "application/json")
                    .body(jsonCollectInfo)
                    .execute();
            final String body = response.body();
            LikeCollectResponse collectResponse = objectMapper.readValue(body, LikeCollectResponse.class);
            return collectResponse.data.resultList;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void dealLikeCollect(List<LikeCollectResponse.LikeCollectDataDetail.ResultList> acquireLikeCollect) {
        if (CollectionUtil.isNotEmpty(acquireLikeCollect)) {
            // 使用Comparator进行排序
            Collections.sort(acquireLikeCollect, (obj1, obj2) -> {
                // 获取content字段
                LikeCollectResponse.LikeCollectDataDetail.ResultList.ContentInfo content1 = obj1.getContent();
                LikeCollectResponse.LikeCollectDataDetail.ResultList.ContentInfo content2 = obj2.getContent();
                // 获取isFans字段，默认为false
                boolean isFans1 = content1 != null && content1.isFans();
                boolean isFans2 = content2 != null && content2.isFans();
                // 根据isFans字段进行比较
                if (isFans1 && !isFans2) {
                    return -1; // obj1排在前面
                } else if (!isFans1 && isFans2) {
                    return 1; // obj2排在前面
                } else {
                    return 0; // 保持原有顺序
                }
            });
            for (LikeCollectResponse.LikeCollectDataDetail.ResultList resultList : acquireLikeCollect) {
                final LikeCollectResponse.LikeCollectDataDetail.ResultList.ContentInfo content = resultList.content;
                final String usernames = content.getUsernames();
                if (StringUtils.isNotEmpty(usernames)) {
                    final String[] names = usernames.split(",");
                    for (int i = 0; i < names.length; i++) {
                        String name = names[i];
                        final String nicknames = content.getNicknames();
                        String nickName = name;
                        if (StringUtils.isNotEmpty(nicknames)) {
                            nickName = names[i];
                        }
                        final List<BusinessInfoResponse.ArticleData.Article> articles10 = csdnArticleInfoService.getArticles10(name);
                        if (CollectionUtil.isNotEmpty(articles10)) {
                            CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(name);
                            if (Objects.isNull(csdnUserInfo)) {
                                csdnUserInfo = new CsdnUserInfo();
                                csdnUserInfo.setUserName(name);
                                csdnUserInfo.setNickName(nickName);
                                csdnUserInfo.setLikeStatus(0);
                                csdnUserInfo.setCollectStatus(0);
                                csdnUserInfo.setCommentStatus(0);
                                csdnUserInfo.setUserWeight(7);
                                csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + name);
                                csdnUserInfoService.save(csdnUserInfo);
                            }
                            csdnService.singleArticle(csdnUserInfo);
                            csdnMessageService.dealMessageByUserName(name);
                        }
                    }
                } else {
                    final String username = content.getUsername();
                    final String nickname = content.getNickname();
                    final List<BusinessInfoResponse.ArticleData.Article> articles10 = csdnArticleInfoService.getArticles10(username);
                    if (CollectionUtil.isNotEmpty(articles10)) {
                        CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
                        if (Objects.isNull(csdnUserInfo)) {
                            csdnUserInfo = new CsdnUserInfo();
                            csdnUserInfo.setUserName(username);
                            csdnUserInfo.setNickName(nickname);
                            csdnUserInfo.setLikeStatus(0);
                            csdnUserInfo.setCollectStatus(0);
                            csdnUserInfo.setCommentStatus(0);
                            csdnUserInfo.setUserWeight(7);
                            csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + username);
                            csdnUserInfoService.save(csdnUserInfo);
                        }
                        csdnService.singleArticle(csdnUserInfo);
                        csdnMessageService.dealMessageByUserName(username);
                    }
                }
            }
        }
    }
}