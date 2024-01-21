package com.kwan.springbootkwan.service;

import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;

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
     * 多人三连
     *
     * @param csdnUserInfos
     */
    void extractSingleArticle(List<CsdnUserInfo> csdnUserInfos);


    /**
     * 根据文章三连
     *
     * @param csdnUserInfo
     * @param csdnArticleInfo
     */
    void tripletByArticle(CsdnUserInfo csdnUserInfo, CsdnArticleInfo csdnArticleInfo);

    /**
     * 自刷流量
     */
    void autoAddView(List<CsdnArticleInfo> articles);

    /**
     * 根据url刷流量
     *
     * @param url
     */
    void autoAddViewByUrl(String url);

    /**
     * 处理私信三连
     */
    void dealTriplet();
}