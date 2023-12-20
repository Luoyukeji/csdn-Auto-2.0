package com.kwan.springbootkwan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnRedPackageDetailInfo;
import com.kwan.springbootkwan.entity.dto.CsdnTotalIncomeDTO;
import com.kwan.springbootkwan.entity.query.CsdnTotalIncomeQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CsdnRedPackageDetailInfoMapper extends BaseMapper<CsdnRedPackageDetailInfo> {


    /**
     * 指定日期的理论最大值
     *
     * @param date
     * @return
     */
    String theoryMaxAmount(@Param("date") String date);

    /**
     * 分页查询数据
     *
     *
     * @param page
     * @param query
     * @return
     */
    Page<CsdnTotalIncomeDTO> totalPageInfo(Page page, @Param("query") CsdnTotalIncomeQuery query);

}

