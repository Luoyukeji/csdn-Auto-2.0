package com.kwan.springbootkwan.service;


import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;


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