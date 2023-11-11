package com.kwan.springbootkwan.service;


import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;

/**
 * 收藏
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/10/24 01:00
 */
public interface CsdnCollectService {

    /**
     * 查询是否收藏过
     *
     * @return
     */
    Boolean isCollect(String articleId, CsdnUserInfo csdnUserInfo);

    /**
     * 收藏
     *
     * @return
     */
    Boolean collect(BusinessInfoResponse.ArticleData.Article article, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo);
}