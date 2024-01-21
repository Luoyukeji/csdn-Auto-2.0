package com.kwan.springbootkwan.service;


import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;


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
    Boolean collect(CsdnArticleInfo csdnArticleInfo, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo);
}