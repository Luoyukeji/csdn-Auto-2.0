package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnFollowFansInfo;

import java.util.List;

/**
 * csdn关注与粉丝信息(CsdnFollowFansInfo)表服务接口
 *
 * @author makejava
 * @since 2023-11-18 16:28:37
 */
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

}

