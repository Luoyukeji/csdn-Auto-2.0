package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.query.CsdnUserInfoQuery;

import java.util.List;

/**
 * csdn用户信息(CsdnUserInfo)表服务接口
 *
 * @author makejava
 * @since 2023-10-23 16:03:14
 */
public interface CsdnUserInfoService extends IService<CsdnUserInfo> {


    /**
     * 通过用户名获取user信息
     *
     * @return
     */
    CsdnUserInfo getUserByUserName(String username);

    /**
     * 通过用户名获取user信息
     *
     * @return
     */
    List<CsdnUserInfo> getAllUser();

    /**
     * 查询等待评论的用户
     *
     * @return
     */
    List<CsdnUserInfo> waitForCommentsUser();

    /**
     * 新增用户
     *
     * @param addInfo
     */
    void add(CsdnUserInfoQuery addInfo);

    /**
     * 检查用户的状态
     *
     * @param csdnTripletDayInfo
     * @param csdnUserInfo
     * @param article
     */
    void checkUserStatus(CsdnTripletDayInfo csdnTripletDayInfo, CsdnUserInfo csdnUserInfo, BusinessInfoResponse.ArticleData.Article article);

}

