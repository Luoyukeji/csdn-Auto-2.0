package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.springbootkwan.entity.CsdnAccountManagement;
import com.kwan.springbootkwan.entity.dto.CsdnAccountTotalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CsdnAccountManagementMapper extends BaseMapper<CsdnAccountManagement> {

    /**
     * 累计金额
     *
     * @return
     */
    List<CsdnAccountTotalDTO> totalInfo();
}

