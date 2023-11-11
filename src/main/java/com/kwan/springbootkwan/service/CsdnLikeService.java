package com.kwan.springbootkwan.service;


import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;

/**
 * 点赞
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/10/24 00:19
 */
public interface CsdnLikeService {

    /**
     * 查询是否点过赞
     *
     * @return
     */
    Boolean isLike(String articleId, CsdnUserInfo csdnUserInfo);

    /**
     * 点赞和取消点赞接口,true,点过,false,没有点过
     *
     * @return
     */
    Boolean like(String articleId, CsdnUserInfo csdnUserInfo, CsdnTripletDayInfo csdnTripletDayInfo);
}