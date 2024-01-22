package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.query.CsdnUserInfoQuery;

import java.util.List;


/**
 * 用户相关业务
 *
 * @author : qinyingjie
 * @version : 2.2.0
 * @date : 2024/1/22 16:32
 */

public interface CsdnUserInfoService extends IService<CsdnUserInfo> {


    /**
     * 通过用户名获取用户信息
     *
     * @return
     */
    CsdnUserInfo getUserByUserName(String username);

    /**
     * 获取用户信息
     *
     * @param userName
     * @param nickName
     * @return
     */
    CsdnUserInfo getUserInfo(String userName, String nickName);

    /**
     * 获取用户信息
     *
     * @param userName
     * @return
     */
    CsdnUserInfo getUserCsdnApi(String userName);

    /**
     * 通过用户名获取user信息
     *
     * @return
     */
    List<CsdnUserInfo> allUser();

    /**
     * 没有头像的用户
     *
     * @return
     */
    List<CsdnUserInfo> allNoAvatarUser();

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
     * 更新用户头像
     */
    void updateAvatar();

    /**
     * 获取用户头像
     *
     * @param userName
     * @return
     */
    String getAvatar(String userName);


    /**
     * 更新用户的三连状态
     */
    void updateUserInfo();
}

