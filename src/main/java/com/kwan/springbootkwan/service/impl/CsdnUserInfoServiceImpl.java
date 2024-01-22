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
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CsdnUserSearch;
import com.kwan.springbootkwan.entity.query.CsdnUserInfoQuery;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.mapper.CsdnUserInfoMapper;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CsdnUserInfoServiceImpl extends ServiceImpl<CsdnUserInfoMapper, CsdnUserInfo> implements CsdnUserInfoService {

    @Value("${csdn.cookie}")
    private String csdnCookie;

    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;

    @Override
    public CsdnUserInfo getUserByUserName(String userName) {
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_delete", 0);删除的人也要能查到,不然会重复加入
        wrapper.eq("user_name", userName).last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public CsdnUserInfo getUserInfo(String userName, String nickName) {
        CsdnUserInfo userInfo = this.getUserByUserName(userName);
        if (Objects.isNull(userInfo)) {
            userInfo = new CsdnUserInfo();
            userInfo.setUserName(userName);
            userInfo.setNickName(nickName);
            userInfo.setLikeStatus(0);
            userInfo.setCollectStatus(0);
            userInfo.setCommentStatus(0);
            userInfo.setUserWeight(7);
            userInfo.setUserHomeUrl("https://blog.csdn.net/" + userName);
            this.save(userInfo);
        }
        return userInfo;
    }

    @Override
    public CsdnUserInfo getUserCsdnApi(String userName) {
        final CsdnUserInfo userByUserName = this.getUserByUserName(userName);
        if (Objects.nonNull(userByUserName)) {
            return userByUserName;
        }
        String url = "https://so.csdn.net/api/v3/search?q=" + userName + "&t=userinfo";
        HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).form("q", userName).form("t", "userinfo").execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
            if (Objects.nonNull(csdnUserSearch)) {
                final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                if (CollectionUtil.isNotEmpty(result_vos)) {
                    for (CsdnUserSearch.CsdnUserSearchInner result_vo : result_vos) {
                        final String username = result_vo.getUsername();
                        if (StringUtils.equals(username, userName)) {
                            final String nickname = result_vo.getNickname();
                            if (StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(username)) {
                                return this.getUserInfo(username, nickname);
                            }
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CsdnUserInfo> allUser() {
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("user_weight");
        return this.list(wrapper);
    }

    @Override
    public List<CsdnUserInfo> allNoAvatarUser() {
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.isNull("avatar");
        return this.list(wrapper);
    }

    @Override
    public List<CsdnUserInfo> waitForCommentsUser() {
        List<CsdnUserInfo> pendingList = new ArrayList<>();
        while (pendingList.size() < 3) {
            QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
            wrapper.in("comment_status", 0, 2, 3, 4, 5)
                    .eq("is_delete", 0)
                    .eq("article_type", CommonConstant.BlogType.BLOG)
                    .last(" order by rand() limit 3");
            List<CsdnUserInfo> list = this.list(wrapper);
            if (CollectionUtil.isNotEmpty(list)) {
                for (CsdnUserInfo csdnUserInfo : list) {
                    final String userName = csdnUserInfo.getUserName();
                    final String nickName = csdnUserInfo.getNickName();
                    final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                    if (Objects.nonNull(articleInfo)) {
                        final Integer likeStatus = articleInfo.getLikeStatus();
                        final Integer collectStatus = articleInfo.getCollectStatus();
                        final Integer commentStatus = articleInfo.getCommentStatus();
                        if (Objects.isNull(likeStatus) || Objects.isNull(collectStatus) || Objects.isNull(commentStatus)) {
                            csdnUserInfo.setLikeStatus(0);
                            csdnUserInfo.setCollectStatus(0);
                            csdnUserInfo.setCommentStatus(0);
                            this.updateById(csdnUserInfo);
                        } else if ((likeStatus == 1 || likeStatus == 9) && (collectStatus == 1 || collectStatus == 9) && (commentStatus == 1 || commentStatus == 9)) {
                            csdnUserInfo.setLikeStatus(1);
                            csdnUserInfo.setCollectStatus(1);
                            csdnUserInfo.setCommentStatus(1);
                            this.updateById(csdnUserInfo);
                        } else {
                            pendingList.add(csdnUserInfo);
                        }
                    }
                }
            }
        }
        return pendingList;
    }

    @Override
    public void add(CsdnUserInfoQuery addInfo) {
        final String userName = addInfo.getUserName();
        final Integer addType = addInfo.getAddType();
        if (StringUtils.isNotEmpty(userName)) {
            // 批量添加
            if (addType == 1) {
                final String[] split = userName.split("\n");
                for (String str : split) {
                    str = str.trim();
                    if (StringUtils.isNotEmpty(str)) {
                        CsdnUserInfo csdnUserInfo = this.getUserByUserName(str);
                        if (csdnUserInfo == null) {
                            csdnUserInfo = new CsdnUserInfo();
                            BeanUtils.copyProperties(addInfo, csdnUserInfo);
                            csdnUserInfo.setUserName(str);
                            csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + str);
                            this.save(csdnUserInfo);
                        }
                        addInfo.setNickName(csdnUserInfo.getNickName());
                    }
                }
            } else {
                CsdnUserInfo csdnUserInfo = this.getUserByUserName(userName);
                if (csdnUserInfo == null) {
                    csdnUserInfo = new CsdnUserInfo();
                    BeanUtils.copyProperties(addInfo, csdnUserInfo);
                    csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + userName);
                    this.save(csdnUserInfo);
                }
                addInfo.setNickName(csdnUserInfo.getNickName());
            }
        }
    }

    @Override
    public void updateAvatar() {
        final List<CsdnUserInfo> csdnUserInfos = this.allNoAvatarUser();
        if (CollectionUtil.isNotEmpty(csdnUserInfos)) {
            for (CsdnUserInfo csdnUserInfo : csdnUserInfos) {
                final String avatar = this.getAvatar(csdnUserInfo.getUserName());
                if (StringUtils.isNotEmpty(avatar)) {
                    this.updateById(csdnUserInfo);
                }
            }
        }
    }


    @Override
    public String getAvatar(String userName) {
        String url = "https://so.csdn.net/api/v3/search?q=" + userName + "&t=userinfo&p=1&s=0&tm=0&lv=-1&ft=0&l=&u=&ct=-1&pnt=-1&ry=-1&ss=-1&dct=-1&vco=-1&cc=-1&sc=-1&akt=-1&art=-1&ca=-1&prs=&pre=&ecc=-1&ebc=-1&urw=&ia=1&dId=&cl=-1&scl=-1&tcl=-1&platform=pc&ab_test_code_overlap=&ab_test_random_code=";
        HttpResponse response = HttpUtil.createGet(url).header("Cookie", csdnCookie).execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final CsdnUserSearch csdnUserSearch = objectMapper.readValue(body, CsdnUserSearch.class);
            if (Objects.nonNull(csdnUserSearch)) {
                final List<CsdnUserSearch.CsdnUserSearchInner> result_vos = csdnUserSearch.getResult_vos();
                if (CollectionUtil.isNotEmpty(result_vos)) {
                    for (CsdnUserSearch.CsdnUserSearchInner result_vo : result_vos) {
                        final String username = result_vo.getUsername();
                        if (StringUtils.equalsIgnoreCase(username, userName)) {
                            return result_vo.getUser_pic();
                        }
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateUserInfo() {
        final List<CsdnUserInfo> allUser = this.allUser();
        if (CollectionUtil.isNotEmpty(allUser)) {
            for (CsdnUserInfo csdnUserInfo : allUser) {
                final String nickName = csdnUserInfo.getNickName();
                final String userName = csdnUserInfo.getUserName();
                final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                if (Objects.nonNull(articleInfo)) {
                    final String url = articleInfo.getArticleUrl();
                    final String currBlogUrl = csdnUserInfo.getCurrBlogUrl();
                    if (!StringUtils.equals(currBlogUrl, url)) {
                        csdnUserInfo.setArticleType(articleInfo.getArticleType());
                        csdnUserInfo.setCurrBlogUrl(articleInfo.getArticleUrl());
                        csdnUserInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
                        csdnUserInfo.setCollectStatus(CommentStatus.UN_PROCESSED.getCode());
                        csdnUserInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
                        this.updateById(csdnUserInfo);
                    }
                }
            }
        }
    }
}