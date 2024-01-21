package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.CsdnUserSearch;

import java.util.List;


public interface CsdnArticleInfoService extends IService<CsdnArticleInfo> {


    /**
     * 根据用户昵称和用户名查询博客
     *
     * @param nickName
     * @param userName
     * @return
     */
    CsdnArticleInfo getArticle(String nickName, String userName);

    /**
     * 获取最新的10篇文章
     *
     * @param nickName
     * @param userName
     * @return
     */
    List<CsdnArticleInfo> getArticles10(String nickName, String userName);

    /**
     * 获取最新的100篇文章
     *
     * @param nickName
     * @param userName
     * @return
     */
    List<CsdnArticleInfo> getArticles100(String nickName, String userName);

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
     * 查看某一文章的阅读量
     *
     * @param userName
     * @param articleId
     * @return
     */
    Integer getViewCount(String userName, String articleId);

    /**
     * 同步本人博客
     */
    void syncMyBlog();

    /**
     * 删除低分文章
     */
    void deleteLowBlog();

    /**
     * 通过全局搜索保存文章
     *
     * @param searchInner
     * @return
     */
    CsdnArticleInfo getArticleBySearchAll(CsdnUserSearch.CsdnUserSearchInner searchInner);

}

