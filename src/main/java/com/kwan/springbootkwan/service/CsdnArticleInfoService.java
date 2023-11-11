package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;

import java.util.List;

/**
 * csdn文章信息(CsdnArticleInfo)表服务接口
 *
 * @author makejava
 * @since 2023-10-28 01:58:46
 */
public interface CsdnArticleInfoService extends IService<CsdnArticleInfo> {
    /**
     * 通过文章id获取文章信息
     *
     * @return
     */
    CsdnArticleInfo getArticleByArticleId(String articleId);

    /**
     * 保存文章Blog
     *
     * @return
     */
    void saveArticle(CsdnArticleInfo csdnArticleInfo);

    /**
     * 获取最新的10篇文章
     *
     * @param username
     * @return
     */
    List<BusinessInfoResponse.ArticleData.Article> getArticles10(String username);

    /**
     * 获取最新的100篇文章
     *
     * @param username
     * @return
     */
    List<BusinessInfoResponse.ArticleData.Article> getArticles100(String username);

    /**
     * 获取文章质量分
     *
     * @param url
     */
    Integer getArticleScore(String url);

    /**
     * 重置文章的质量分
     */
    void getBlogScore();

    /**
     * 查询文章质量分
     *
     * @param articleUrl
     * @return
     */
    Integer getScore(String articleUrl);

    /**
     * 校验文章的状态
     *
     * @param csdnTripletDayInfo
     * @param csdnArticleInfo
     * @param csdnUserInfo
     */
    void checkBlogStatus(CsdnTripletDayInfo csdnTripletDayInfo, CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo);


    /**
     * 给指定人员添加10篇博客
     *
     * @param username
     */
    void add10Blog(String username, CsdnUserInfo csdnUserInfo);
}

