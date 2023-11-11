package com.kwan.springbootkwan.service;

import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;

import java.util.List;

/**
 * csdn博客自动化
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/10/23 14:59
 */
public interface CsdnService {

    /**
     * 单人三连
     *
     * @return
     */
    void singleArticle(CsdnUserInfo csdnUserInfo);

    /**
     * 多人三连
     */
    void allTriplet();

    /**
     * 根据文章三连
     *
     * @param csdnUserInfo
     * @param article
     */
    void tripletByArticle(CsdnUserInfo csdnUserInfo, BusinessInfoResponse.ArticleData.Article article, CsdnArticleInfo csdnArticleInfo);

    /**
     * 自刷流量
     */
    void autoAddView(List<BusinessInfoResponse.ArticleData.Article> articles );

}