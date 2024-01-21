package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.enums.CollectStatus;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.service.CsdnArticleCommentService;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnArticleLikeService;
import com.kwan.springbootkwan.service.CsdnCollectService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import com.kwan.springbootkwan.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnServiceImpl implements CsdnService {

    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.auto_add_view_count}")
    private Integer autoAddViewCount;
    @Value("${csdn.auto_comment_count}")
    private Integer commentCount;
    @Autowired
    private CsdnMessageService csdnMessageService;
    @Autowired
    private CsdnCollectService csdnCollectService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleLikeService csdnArticleLikeService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnArticleCommentService csdnArticleCommentService;
    @Autowired
    private CsdnTripletDayInfoService csdnTripletDayInfoService;

    @Override
    public void extractSingleArticle(List<CsdnUserInfo> csdnUserInfos) {
        if (CollectionUtil.isNotEmpty(csdnUserInfos)) {
            for (CsdnUserInfo csdnUserInfo : csdnUserInfos) {
                final String userName = csdnUserInfo.getUserName();
                final String nickName = csdnUserInfo.getNickName();
                final CsdnArticleInfo csdnArticleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                if (Objects.nonNull(csdnArticleInfo)) {
                    this.tripletByArticle(csdnUserInfo, csdnArticleInfo);
                }
            }
        }
    }

    /**
     * 根据文章三连
     *
     * @param csdnUserInfo
     * @param csdnArticleInfo
     */
    @Override
    public void tripletByArticle(CsdnUserInfo csdnUserInfo, CsdnArticleInfo csdnArticleInfo) {
        final CsdnTripletDayInfo dayInfo = csdnTripletDayInfoService.todayInfo();
        final String articleId = csdnArticleInfo.getArticleId();
        // 点赞
        final Boolean isLike = csdnArticleLikeService.isLike(articleId, csdnUserInfo);
        if (isLike) {
            csdnUserInfo.setLikeStatus(LikeStatus.HAVE_ALREADY_LIKED.getCode());
        } else {
            csdnArticleLikeService.like(articleId, csdnUserInfo, dayInfo);
        }
        // 收藏
        final Boolean collect = csdnCollectService.isCollect(articleId, csdnUserInfo);
        if (collect) {
            csdnUserInfo.setCollectStatus(CollectStatus.HAVE_ALREADY_COLLECT.getCode());
        } else {
            csdnCollectService.collect(csdnArticleInfo, csdnUserInfo, dayInfo);
        }
        // 评论
        final Integer commentStatus = dayInfo.getCommentStatus();
        final Integer commentNum = dayInfo.getCommentNum();
        final Boolean comment = csdnArticleCommentService.isComment(csdnArticleInfo, csdnUserInfo);
        if (comment) {
            csdnUserInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
        } else if (commentNum >= commentCount) {
            log.info("评论次数达到上限，无法评论");
            csdnUserInfo.setCommentStatus(CommentStatus.COMMENT_NUM_40.getCode());
            dayInfo.setCommentStatus(CommentStatus.COMMENT_NUM_40.getCode());
        } else if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatus)
                || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatus)
                || CommentStatus.COMMENT_NUM_40.getCode().equals(commentStatus)) {
            csdnUserInfo.setCommentStatus(commentStatus);
        } else {
            csdnArticleCommentService.comment(articleId, csdnUserInfo, dayInfo);
        }
        csdnArticleInfo.setLikeStatus(csdnUserInfo.getLikeStatus());
        csdnArticleInfo.setCollectStatus(csdnUserInfo.getCollectStatus());
        csdnArticleInfo.setCommentStatus(csdnUserInfo.getCommentStatus());
        csdnTripletDayInfoService.updateById(dayInfo);
        csdnUserInfoService.updateById(csdnUserInfo);
        csdnArticleInfoService.updateById(csdnArticleInfo);
    }

    @Override
    public void autoAddView(List<CsdnArticleInfo> articles) {
        final int size = articles.size();
        log.info("autoAddView() called with: articles size= {}", size);
        for (int i = 1; i <= autoAddViewCount; i++) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("开始自动刷流量");
            for (CsdnArticleInfo article : articles) {
                final String blogUrl = article.getArticleUrl();
                String ip = IPUtil.generateRandomIP();
                HttpURLConnection con = null;
                try {
                    URL url = new URL(blogUrl);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestProperty("X-Forward-For", ip);
                    con.setDoOutput(true);
                    con.setRequestMethod("GET");
                    con.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                    con.connect();
                    log.info("这是您当前文章被刷新的第{}次。刷新浏览量请求状态码为：{}", i, con.getResponseCode());
                    log.info("文章URL为={}", blogUrl);
                    log.info("----------------------------------------------------------------------------");
                } catch (Exception var7) {
                    log.error("刷新本次失败~~~");
                } finally {
                    if (Objects.nonNull(con)) {
                        con.disconnect();
                    }
                }
            }
            stopWatch.stop();
            try {
                log.info("耗时统计信息:{}", new ObjectMapper().writeValueAsString(stopWatch.getTaskInfo()));
                log.info("耗时秒数:{}", new ObjectMapper().writeValueAsString(stopWatch.getTotalTimeSeconds()));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void autoAddViewByUrl(String urlBlog) {
        for (int i = 1; i <= 100; i++) {
            String ip = IPUtil.generateRandomIP();
            HttpURLConnection con = null;
            try {
                URL url = new URL(urlBlog);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestProperty("X-Forward-For", ip);
                con.setDoOutput(true);
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
                con.connect();
                log.info("这是您当前文章被刷新的第{}次。刷新浏览量请求状态码为：{}", i, con.getResponseCode());
                log.info("文章URL为={}", urlBlog);
                log.info("----------------------------------------------------------------------------");
                Thread.sleep(20000);
            } catch (Exception var7) {
                log.error("刷新本次失败~~~");
            } finally {
                if (Objects.nonNull(con)) {
                    con.disconnect();
                }
            }
        }
    }

    /**
     * 处理私信三连
     */
    @Override
    public void dealTriplet() {
        final List<CsdnUserInfo> csdnUserInfos = csdnMessageService.acquireMessage();
        if (CollectionUtil.isNotEmpty(csdnUserInfos)) {
            for (CsdnUserInfo csdnUserInfo : csdnUserInfos) {
                final String userName = csdnUserInfo.getUserName();
                final String nickName = csdnUserInfo.getNickName();
                final CsdnArticleInfo csdnArticleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                if (Objects.nonNull(csdnArticleInfo)) {
                    this.tripletByArticle(csdnUserInfo, csdnArticleInfo);
                    csdnMessageService.sendMessage(csdnArticleInfo);
                }
            }
        }
    }
}