package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;

/**
 * 每日三连监控(CsdnTripletDayInfo)表服务接口
 *
 * @author makejava
 * @since 2023-10-26 20:55:31
 */
public interface CsdnTripletDayInfoService extends IService<CsdnTripletDayInfo> {


    /**
     * 获取新的一天的三连统计数据
     *
     * @return
     */
    CsdnTripletDayInfo todayInfo();
   /**
     * 获取新的一天的三连统计数据
     *
     * @return
     */
    void addAll();

    /**
     * 重置三连管理的星期字段
     */
    void resetWeekInfo();

}

