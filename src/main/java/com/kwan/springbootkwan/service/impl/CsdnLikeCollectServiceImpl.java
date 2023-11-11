package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.LikeCollectQuery;
import com.kwan.springbootkwan.entity.csdn.LikeCollectResponse;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
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
public class CsdnLikeCollectServiceImpl implements CsdnLikeCollectService {

    @Value("${csdn.cookie}")
    private String csdnCookie;

    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;

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
            for (LikeCollectResponse.LikeCollectDataDetail.ResultList resultList : acquireLikeCollect) {
                final LikeCollectResponse.LikeCollectDataDetail.ResultList.ContentInfo content = resultList.content;
                final String usernames = content.getUsernames();
                if (StringUtils.isNotEmpty(usernames)) {
                    final String[] names = usernames.split(",");
                    for (String name : names) {
                        CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(name);
                        if (Objects.nonNull(csdnUserInfo)) {
                            csdnService.singleArticle(csdnUserInfo);
                        }
                    }
                } else {
                    final String username = content.getUsername();
                    CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
                    if (Objects.nonNull(csdnUserInfo)) {
                        csdnService.singleArticle(csdnUserInfo);
                    }
                }
            }
        }
    }
}