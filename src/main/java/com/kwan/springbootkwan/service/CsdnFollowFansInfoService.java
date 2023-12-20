package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnFollowFansInfo;

import java.util.List;


public interface CsdnFollowFansInfoService extends IService<CsdnFollowFansInfo> {

    /**
     * 获取所有的粉丝
     *
     * @return
     */
    List<CsdnFollowFansInfo> getAll();


    /**
     * 根据用户名查询粉丝
     *
     * @param userName
     * @return
     */
    CsdnFollowFansInfo getByUserName(String userName);


    /**
     * 仅仅是已关注
     *
     * @return
     */
    List<CsdnFollowFansInfo> getOnlyFollow();

    /**
     * 保存我的粉丝
     */
    void saveFans();

    /**
     * 获取我关注的
     */
    void saveFollow();

    /**
     * 取消已关注,并删除
     */
    void deleteFollow();

    /**
     * 取消关注23开头的号
     */
    void deleteFollowStart23();

    /**
     * 互关用户最新发布时间更新
     */
    void updatePostTime();

    /**
     * 获取通知发红包的用户
     *
     * @return
     */
    List<CsdnFollowFansInfo> noticeUsers(Integer number);

    /**
     * 是否是互关
     *
     * @param username
     * @return
     */
    boolean isIntercorrelation(String username);

    /**
     * 取消没有文章的,并删除
     */
    void deleteNoArticle();

    /**
     * 查询关注的
     *
     * @return
     */
    List<CsdnFollowFansInfo> getConcernCorrelation();
}

