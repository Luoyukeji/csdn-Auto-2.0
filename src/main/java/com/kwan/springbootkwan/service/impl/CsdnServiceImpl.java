package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.StopWatch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnCollectService;
import com.kwan.springbootkwan.service.CsdnCommentService;
import com.kwan.springbootkwan.service.CsdnLikeService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import com.kwan.springbootkwan.utils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    @Autowired
    private CsdnLikeService csdnLikeService;
    @Autowired
    private CsdnCollectService csdnCollectService;
    @Autowired
    private CsdnCommentService csdnCommentService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnTripletDayInfoService csdnTripletDayInfoService;

    @Override
    public void singleArticle(CsdnUserInfo csdnUserInfo) {
        final String username = csdnUserInfo.getUserName();
        List<BusinessInfoResponse.ArticleData.Article> list = csdnArticleInfoService.getArticles10(username);
        if (CollectionUtil.isNotEmpty(list)) {
            for (int i = 0; i < 1; i++) {
                final BusinessInfoResponse.ArticleData.Article article = list.get(i);
                final String type = article.getType();
                if (!StringUtils.equals("blog", type)) {
                    csdnUserInfo.setArticleType(type);
                    csdnUserInfoService.updateById(csdnUserInfo);
                    continue;
                }
                //先去查询文章,没有查到的话就插入文章
                final String articleUrl = article.getUrl();
                String articleIdFormUrl = articleUrl.substring(articleUrl.lastIndexOf("/") + 1);
                final Object articleId = article.getArticleId();
                if (Objects.isNull(articleId)) {
                    article.setArticleId(articleIdFormUrl);
                }
                CsdnArticleInfo csdnArticleInfo = this.csdnArticleInfoService.getArticleByArticleId(article.getArticleId());
                if (Objects.isNull(csdnArticleInfo)) {
                    csdnArticleInfo = new CsdnArticleInfo();
                    csdnArticleInfo.setArticleId(article.getArticleId().toString());
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
                //不能对自己三连
                if (!StringUtils.equals(selfUserName, username)) {
                    this.tripletByArticle(csdnUserInfo, article, csdnArticleInfo);
                }
            }
        }
    }

    @Override
    public void allTriplet() {
        final List<CsdnUserInfo> allUser = csdnUserInfoService.getAllUser();
        if (CollectionUtil.isNotEmpty(allUser)) {
            for (CsdnUserInfo csdnUserInfo : allUser) {
                singleArticle(csdnUserInfo);
            }
        }
        log.info("全部三连完成");
    }

    /**
     * 根据文章三连
     *
     * @param csdnUserInfo
     * @param article
     */
    @Override
    public void tripletByArticle(CsdnUserInfo csdnUserInfo, BusinessInfoResponse.ArticleData.Article article, CsdnArticleInfo csdnArticleInfo) {
        //获取文章id
        final String urlInfo = article.getUrl();
        String articleId = urlInfo.substring(urlInfo.lastIndexOf("/") + 1);
        //获取每日三连总信息
        final CsdnTripletDayInfo dayInfo = csdnTripletDayInfoService.todayInfo();
        if (Objects.nonNull(dayInfo)) {
            final Integer commentStatus = dayInfo.getCommentStatus();
            //点赞
            final Boolean isLike = csdnLikeService.isLike(articleId, csdnUserInfo);
            if (isLike) {
                csdnUserInfo.setLikeStatus(LikeStatus.HAVE_ALREADY_LIKED.getCode());
            } else {
                csdnLikeService.like(articleId, csdnUserInfo, dayInfo);
            }
            //收藏
            final Boolean collect = csdnCollectService.isCollect(articleId, csdnUserInfo);
            if (!collect) {
                csdnCollectService.collect(article, csdnUserInfo, dayInfo);
            }
            //评论
            final Integer commentNum = dayInfo.getCommentNum();
            final Boolean comment = csdnCommentService.isComment(article, csdnUserInfo);
            if (comment) {
                csdnUserInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
            } else if (commentNum >= 42) {
                csdnUserInfo.setCommentStatus(CommentStatus.COMMENT_NUM_49.getCode());
                dayInfo.setCommentStatus(CommentStatus.COMMENT_NUM_49.getCode());
            } else if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatus)
                    || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatus)
                    || CommentStatus.COMMENT_NUM_49.getCode().equals(commentStatus)) {
                csdnUserInfo.setCommentStatus(commentStatus);
            } else {
                csdnCommentService.comment(articleId, csdnUserInfo, dayInfo);
            }
            csdnTripletDayInfoService.updateById(dayInfo);
            csdnUserInfoService.updateById(csdnUserInfo);
            csdnArticleInfo.setLikeStatus(csdnUserInfo.getLikeStatus());
            csdnArticleInfo.setCollectStatus(csdnUserInfo.getCollectStatus());
            csdnArticleInfo.setCommentStatus(csdnUserInfo.getCommentStatus());
            csdnArticleInfoService.updateById(csdnArticleInfo);
        }
    }

    @Override
    public void autoAddView(List<BusinessInfoResponse.ArticleData.Article> articles) {
        log.info("autoAddView() called with: articles size= {}", articles.size());
        for (int i = 1; i <= autoAddViewCount; i++) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start("开始自动刷流量");
            for (BusinessInfoResponse.ArticleData.Article article : articles) {
                final String blogUrl = article.getUrl();
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
                Thread.sleep(60000);
            } catch (Exception var7) {
                log.error("刷新本次失败~~~");
            } finally {
                if (Objects.nonNull(con)) {
                    con.disconnect();
                }
            }
        }
    }
}