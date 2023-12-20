package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnAccountManagement;
import com.kwan.springbootkwan.entity.csdn.CurrentAmount;
import com.kwan.springbootkwan.entity.dto.CsdnAccountTotalDTO;

import java.util.List;


public interface CsdnAccountManagementService extends IService<CsdnAccountManagement> {


    /**
     * 根据余额单号查询余额信息
     *
     * @param orderNo
     * @return
     */
    CsdnAccountManagement getAccountInfo(String orderNo);

    /**
     * 新增余额信息
     */
    void addAccountInfo();

    /**
     * 同步最近5页数据
     */
    void add5AccountInfo();

    /**
     * 获取当前余额
     */
    CurrentAmount.CurrentAmountData.CurrentAmountDetail currentAmount();
    /**
     * 累计总金额
     *
     * @return
     */
    List<CsdnAccountTotalDTO> totalInfo();

}

