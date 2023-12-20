package com.kwan.springbootkwan.controller;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnAccountManagement;
import com.kwan.springbootkwan.entity.PageBean;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.csdn.CurrentAmount;
import com.kwan.springbootkwan.entity.dto.CsdnAccountManagementAllDTO;
import com.kwan.springbootkwan.entity.dto.CsdnAccountManagementDTO;
import com.kwan.springbootkwan.entity.dto.CsdnAccountTotalDTO;
import com.kwan.springbootkwan.entity.query.CsdnAccountManagementQuery;
import com.kwan.springbootkwan.service.CsdnAccountManagementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@Slf4j
@RestController
@Api(tags = "csdn余额管理")
@RequestMapping("/csdnAccountManagement")

public class CsdnAccountManagementController {

    @Resource
    private CsdnAccountManagementService csdnAccountManagementService;

    @ApiOperation(value = "同步全量余额信息", nickname = "同步全量余额信息")
    @GetMapping("/addAccountInfo")
    public Result addAccountInfo() {
        csdnAccountManagementService.addAccountInfo();
        return Result.ok("同步全量余额信息成功");
    }

    @ApiOperation(value = "同步最近5页余额信息", nickname = "同步最近5页余额信息")
    @GetMapping("/add5AccountInfo")
    public Result add5AccountInfo() {
        csdnAccountManagementService.add5AccountInfo();
        return Result.ok("同步最近5页余额信息成功");
    }

    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnAccountManagementQuery query) {
        CsdnAccountManagementAllDTO response = new CsdnAccountManagementAllDTO();
        final Date startDate = query.getStartDate();
        final Date endDateParam = query.getEndDate();
        Date endDate = null;
        if (Objects.nonNull(endDateParam)) {
            endDate = DateUtil.endOfDay(endDateParam);
        }
        Page<CsdnAccountManagement> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnAccountManagement> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        if (Objects.nonNull(startDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String startFormattedDate = sdf.format(startDate);
            wrapper.ge("time", startFormattedDate);
        }
        if (Objects.nonNull(endDate)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String endFormattedDate = sdf.format(endDate);
            wrapper.le("time", endFormattedDate);
        }
        final Integer operateType = query.getOperateType();
        if (Objects.nonNull(operateType)) {
            wrapper.eq("operate_type", operateType);
        }
        final String productName = query.getProductName();
        if (StringUtils.isNotEmpty(productName)) {
            wrapper.eq("product_name", productName);
        }
        final Integer code = query.getCode();
        if (Objects.nonNull(code)) {
            wrapper.eq("code", code);
        }
        wrapper.orderByDesc("time");
        final PageBean<CsdnAccountManagementDTO> from = CsdnAccountManagementDTO.Converter.INSTANCE.from(this.csdnAccountManagementService.page(pageParm, wrapper));
        final List<CsdnAccountManagementDTO> content = from.getContent();
        if (CollectionUtil.isNotEmpty(content)) {
            response.setCurrentTotal(content.stream().map(CsdnAccountManagementDTO::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        }
        response.setFrom(from);
        final CurrentAmount.CurrentAmountData.CurrentAmountDetail cBeans = csdnAccountManagementService.currentAmount();
        if (Objects.nonNull(cBeans)) {
            response.setCurrentAmount(cBeans.getTotal());
            response.setExpireAmount(cBeans.getExpireAmount());
            response.setNormalAmount(cBeans.getNormalAmount());
        }
        final List<CsdnAccountTotalDTO> csdnAccountTotalDTOS = csdnAccountManagementService.totalInfo();
        if (CollectionUtil.isNotEmpty(csdnAccountTotalDTOS)) {
            for (CsdnAccountTotalDTO csdnAccountTotalDTO : csdnAccountTotalDTOS) {
                final BigDecimal amount = csdnAccountTotalDTO.getAmount();
                if (csdnAccountTotalDTO.getOperateType() == 1) {
                    response.setTotalGetAmount(amount);
                } else {
                    response.setTotalPayAmount(amount);
                }
            }
        }
        return Result.ok(response);
    }

    @ApiOperation(value = "获取当前余额", nickname = "获取当前余额")
    @GetMapping("/currentAmount")
    public Result currentAmount() {
        return Result.ok(csdnAccountManagementService.currentAmount());
    }
}

