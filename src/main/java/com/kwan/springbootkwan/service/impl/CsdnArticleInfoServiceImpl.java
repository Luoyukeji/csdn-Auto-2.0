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
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.csdn.ScoreResponse;
import com.kwan.springbootkwan.enums.CollectStatus;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.mapper.CsdnArticleInfoMapper;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnCollectService;
import com.kwan.springbootkwan.service.CsdnCommentService;
import com.kwan.springbootkwan.service.CsdnLikeService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CsdnArticleInfoServiceImpl extends ServiceImpl<CsdnArticleInfoMapper, CsdnArticleInfo> implements CsdnArticleInfoService {
    @Value("${csdn.cookie}")
    private String csdnCookie;
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.url.user_article_url}")
    private String url;
    @Value("${csdn.url.get_article_score_url}")
    private String getArticleScoreUrl;
    @Resource
    private CsdnLikeService csdnLikeService;
    @Resource
    private CsdnCollectService csdnCollectService;
    @Resource
    private CsdnCommentService csdnCommentService;

    @Override
    public CsdnArticleInfo getArticleByArticleId(String articleId) {
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("article_id", articleId)
                .last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public void saveArticle(CsdnArticleInfo add) {
        final String articleId = add.getArticleId();
        CsdnArticleInfo csdnArticleInfo = this.getArticleByArticleId(articleId);
        if (Objects.isNull(csdnArticleInfo)) {
            csdnArticleInfo = new CsdnArticleInfo();
            csdnArticleInfo.setArticleId(articleId);
            csdnArticleInfo.setUserName(add.getUserName());
            csdnArticleInfo.setArticleTitle(add.getArticleTitle());
            csdnArticleInfo.setArticleDescription(add.getArticleDescription());
            csdnArticleInfo.setArticleUrl(add.getArticleUrl());
            csdnArticleInfo.setArticleScore(add.getArticleScore());
            csdnArticleInfo.setNickName(add.getNickName());
            csdnArticleInfo.setIsMyself(add.getIsMyself());
            this.save(csdnArticleInfo);
        }
    }

    @Override
    public List<BusinessInfoResponse.ArticleData.Article> getArticles10(String username) {
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("page", 1)
                .form("size", 10)
                .form("businessType", "lately")
                .form("noMore", false)
                .form("username", username)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        BusinessInfoResponse businessInfoResponse;
        List<BusinessInfoResponse.ArticleData.Article> list = null;
        try {
            businessInfoResponse = objectMapper.readValue(body, BusinessInfoResponse.class);
            final BusinessInfoResponse.ArticleData data = businessInfoResponse.getData();
            list = data.getList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public List<BusinessInfoResponse.ArticleData.Article> getArticles100(String username) {
        HttpResponse response = HttpUtil.createGet(url)
                .header("Cookie", csdnCookie)
                .form("page", 1)
                .form("size", 100)
                .form("businessType", "lately")
                .form("noMore", false)
                .form("username", username)
                .execute();
        final String body = response.body();
        ObjectMapper objectMapper = new ObjectMapper();
        BusinessInfoResponse businessInfoResponse;
        List<BusinessInfoResponse.ArticleData.Article> list = null;
        try {
            businessInfoResponse = objectMapper.readValue(body, BusinessInfoResponse.class);
            final BusinessInfoResponse.ArticleData data = businessInfoResponse.getData();
            list = data.getList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list;
    }

    @Override
    public Integer getArticleScore(String url) {
        try {
            // 创建自定义TrustManager来绕过SSL验证
            TrustManager[] trustAllCertificates = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        @Override
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            // 创建SSL上下文并初始化
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCertificates, new java.security.SecureRandom());
            // 创建OkHttpClient并设置自定义的SSL上下文
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCertificates[0])
                    .hostnameVerifier((hostname, session) -> true) // 禁用主机名验证
                    .build();
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("url", url)
                    .build();
            Request request = new Request.Builder()
                    .url(getArticleScoreUrl)
                    .method("POST", body)
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Site", "same-site")
                    .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36")
                    .addHeader("sec-ch-ua", "\"Chromium\";v=\"118\", \"Google Chrome\";v=\"118\", \"Not=A?Brand\";v=\"99\"")
                    .addHeader("X-Ca-Signature-Headers", "x-ca-key,x-ca-nonce")
                    .addHeader("X-Ca-Signature", "kYkS7PV3x5M1TsCI22Kn3MqiA/V+OZnIGtdHOxQQ1TI=")
                    .addHeader("X-Ca-Nonce", "d6bedcac-4ab6-4a17-a06d-77c0f2ee3c37")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("X-Ca-Signed-Content-Type", "multipart/form-data")
                    .addHeader("Accept", "application/json, text/plain, */*")
                    .addHeader("X-Ca-Key", "203930474")
                    .addHeader("sec-ch-ua-platform", "\"macOS\"")
                    .addHeader("host", "bizapi.csdn.net")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarySNjHB5BC1gTFn3PG")
                    .build();
            Response response = client.newCall(request).execute();
            // 处理响应
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                System.out.println("Response: " + responseBody);
                ObjectMapper objectMapper = new ObjectMapper();
                ScoreResponse scoreResponse;
                try {
                    scoreResponse = objectMapper.readValue(responseBody, ScoreResponse.class);
                    return scoreResponse.getData().getScore();
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Request failed with response code: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void getBlogScore() {
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("article_score", 0);
        final List<CsdnArticleInfo> list = this.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnArticleInfo csdnArticleInfo : list) {
                final String articleUrl = csdnArticleInfo.getArticleUrl();
                final Integer articleScore;
                try {
                    articleScore = this.getArticleScore(articleUrl);
                    csdnArticleInfo.setArticleScore(articleScore);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.updateById(csdnArticleInfo);
            }
        }
    }

    @Override
    public void add10Blog(String username, CsdnUserInfo csdnUserInfo) {
        final List<BusinessInfoResponse.ArticleData.Article> blogs = this.getArticles10(username);
        if (CollectionUtil.isNotEmpty(blogs)) {
            for (BusinessInfoResponse.ArticleData.Article article : blogs) {
                final String type = article.getType();
                if (StringUtils.equals(type, CommonConstant.ARTICLE_TYPE_BLOG)) {
                    CsdnArticleInfo csdnArticleInfo = this.getArticleByArticleId(article.getArticleId().toString());
                    if (Objects.isNull(csdnArticleInfo)) {
                        csdnArticleInfo = new CsdnArticleInfo();
                        final String url = article.getUrl();
                        csdnArticleInfo.setArticleId(article.getArticleId().toString());
                        csdnArticleInfo.setArticleUrl(url);
                        csdnArticleInfo.setArticleTitle(article.getTitle());
                        csdnArticleInfo.setArticleDescription(article.getDescription());
                        csdnArticleInfo.setUserName(username);
                        csdnArticleInfo.setNickName(csdnUserInfo.getNickName());
                        csdnArticleInfo.setArticleScore(this.getScore(url));
                        if (StringUtils.equals(selfUserName, username)) {
                            csdnArticleInfo.setIsMyself(1);
                        }
                        this.saveArticle(csdnArticleInfo);
                    }
                }
            }
        }
    }

    @Override
    public Integer getScore(String articleUrl) {
        return this.getArticleScore(articleUrl);
    }

    @Override
    public void checkBlogStatus(CsdnTripletDayInfo csdnTripletDayInfo, CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo) {
        final Integer likeStatusQuery = csdnArticleInfo.getLikeStatus();
        final Integer likeStatus = csdnTripletDayInfo.getLikeStatus();
        final Integer commentStatus = csdnTripletDayInfo.getCommentStatus();
        final String articleId = csdnArticleInfo.getArticleId();
        //处理点赞的情况
        if (LikeStatus.LIKE_IS_FULL.getCode().equals(likeStatusQuery)) {
            final Boolean like = csdnLikeService.isLike(articleId, csdnUserInfo);
            if (like) {
                csdnArticleInfo.setLikeStatus(LikeStatus.HAVE_ALREADY_LIKED.getCode());
            } else if (LikeStatus.LIKE_IS_FULL.getCode().equals(likeStatus)) {
                csdnArticleInfo.setLikeStatus(LikeStatus.LIKE_IS_FULL.getCode());
            } else {
                //如果当天是点赞成功状态,则可以点赞,文章点赞状态是未处理状态
                csdnArticleInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
            }
        }
        //收藏
        final Boolean collect = csdnCollectService.isCollect(articleId, csdnUserInfo);
        if (collect) {
            csdnArticleInfo.setCollectStatus(CollectStatus.HAVE_ALREADY_COLLECT.getCode());
        } else if (CollectStatus.COLLECT_IS_FULL.getCode().equals(likeStatus)) {
            csdnArticleInfo.setCollectStatus(CollectStatus.COLLECT_IS_FULL.getCode());
        } else {
            csdnArticleInfo.setCollectStatus(CollectStatus.UN_PROCESSED.getCode());
        }
        //处理评论的情况
        final Integer commentStatusQuery = csdnArticleInfo.getCommentStatus();
        if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatusQuery)
                || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatusQuery)
                || CommentStatus.COMMENT_TOO_FAST.getCode().equals(commentStatusQuery)
                || CommentStatus.COMMENT_NUM_49.getCode().equals(commentStatusQuery)) {
            //查询是否评论过
            BusinessInfoResponse.ArticleData.Article article = new BusinessInfoResponse.ArticleData.Article();
            article.setUrl(csdnArticleInfo.getArticleUrl());
            final Boolean comment = csdnCommentService.isComment(article, csdnUserInfo);
            if (comment) {
                csdnArticleInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
            } else if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatus)
                    || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatus)
                    || CommentStatus.COMMENT_NUM_49.getCode().equals(commentStatus)) {
                csdnArticleInfo.setCommentStatus(commentStatus);
            } else {
                csdnArticleInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
            }
        }
        this.updateById(csdnArticleInfo);
    }
}