package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kwan.springbootkwan.entity.CsdnRedPackage;
import com.kwan.springbootkwan.entity.dto.CsdnDayRedPackageDTO;
import com.kwan.springbootkwan.entity.dto.CsdnSevenDayRedPackageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CsdnRedPackageMapper extends BaseMapper<CsdnRedPackage> {

    /**
     * 每日红包
     *
     * @param min
     * @param max
     * @return
     */
    List<CsdnDayRedPackageDTO> dayRedPackage(@Param("min") String min, @Param("max") String max);

    /**
     * 红包七日概览
     *
     * @param min
     * @param max
     * @return
     */
    CsdnSevenDayRedPackageDTO sevenDayOverview(@Param("min") String min, @Param("max") String max);
}

