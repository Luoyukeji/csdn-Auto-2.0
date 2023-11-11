package com.kwan.springbootkwan.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.CsdnTripletDayInfoDTO;
import com.kwan.springbootkwan.entity.query.CsdnTripletDayInfoQuery;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


@Api(tags = "csdn每日三连监控")
@RestController
@RequestMapping("/dayInfo")
public class CsdnTripletDayInfoController {

    @Autowired
    private CsdnTripletDayInfoService csdnTripletDayInfoService;

    @GetMapping("/add")
    public Result autoReply() {
        return Result.ok(csdnTripletDayInfoService.todayInfo());
    }

    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnTripletDayInfoQuery query) {
        final Date startDate = query.getStartDate();
        final Date endDate = query.getEndDate();
        Page<CsdnTripletDayInfo> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnTripletDayInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        if (Objects.nonNull(startDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String startFormattedDate = sdf.format(startDate);
            wrapper.ge("triplet_date", startFormattedDate);
        }
        if (Objects.nonNull(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String endFormattedDate = sdf.format(endDate);
            wrapper.le("triplet_date", endFormattedDate);
        }
        wrapper.orderByDesc("create_time");
        return Result.ok(CsdnTripletDayInfoDTO.Converter.INSTANCE.from(this.csdnTripletDayInfoService.page(pageParm, wrapper)));
    }
}

