package com.kwan.springbootkwan.service;


import com.kwan.springbootkwan.entity.csdn.LikeCollectResponse;

import java.util.List;

/**
 * 点赞收藏相关
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2023/11/9 23:58
 */
public interface CsdnLikeCollectService {

    /**
     * 获取其他用户给我的点赞和收藏信息
     */
    List<LikeCollectResponse.LikeCollectDataDetail.ResultList> acquireLikeCollect();

    /**
     * 处理其他用户给我的点赞和收藏三连
     *
     * @param acquireLikeCollect
     */
    void dealLikeCollect(List<LikeCollectResponse.LikeCollectDataDetail.ResultList> acquireLikeCollect);

}