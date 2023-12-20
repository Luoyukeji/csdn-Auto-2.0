package com.kwan.springbootkwan.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnFollowFansInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.CsdnFollowFansInfoDTO;
import com.kwan.springbootkwan.entity.query.CsdnFollowFansInfoQuery;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
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
import java.util.Set;


@Slf4j
@RestController
@RequestMapping("/csdn/followFans")
@Api(tags = "csdn关注与粉丝信息API")
public class CsdnFollowFansInfoController {

    @Resource
    private CsdnFollowFansInfoService csdnFollowFansInfoService;


    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnFollowFansInfoQuery query) {
        Page<CsdnFollowFansInfo> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnFollowFansInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        final Set<String> relationType = query.getRelationType();
        if (CollectionUtil.isNotEmpty(relationType)) {
            wrapper.in("relation_type", relationType);
        }
        final String userName = query.getUserName();
        if (StringUtils.isNotEmpty(userName)) {
            wrapper.likeRight("user_name", userName);
        }
        wrapper.orderByDesc("id");
        return Result.ok(CsdnFollowFansInfoDTO.Converter.INSTANCE.from(this.csdnFollowFansInfoService.page(pageParm, wrapper)));
    }

    @ApiOperation(value = "获取我的粉丝", nickname = "获取我的粉丝")
    @GetMapping("/saveFans")
    public Result saveFans() {
        csdnFollowFansInfoService.saveFans();
        return Result.ok("获取我的粉丝完成");
    }

    @ApiOperation(value = "获取我关注的", nickname = "获取我关注的")
    @GetMapping("/saveFollow")
    public Result saveFollow() {
        csdnFollowFansInfoService.saveFollow();
        return Result.ok("获取我关注的完成");
    }

    @ApiOperation(value = "取消已关注,并删除", nickname = "取消已关注,并删除")
    @GetMapping("/deleteFollow")
    public Result deleteFollow() {
        csdnFollowFansInfoService.saveFans();
        csdnFollowFansInfoService.saveFollow();
        csdnFollowFansInfoService.deleteFollow();
        return Result.ok("取消已关注,并删除完成");
    }


    @ApiOperation(value = "取消23开头的已关注,并删除", nickname = "取消23开头的已关注,并删除")
    @GetMapping("/deleteFollowStart23")
    public Result deleteFollowStart23() {
        csdnFollowFansInfoService.deleteFollowStart23();
        return Result.ok("取消23开头的已关注,并删除完成");
    }

    @ApiOperation(value = "互关用户最新发布时间更新", nickname = "互关用户最新发布时间更新")
    @GetMapping("/updatePostTime")
    public Result updatePostTime() {
        csdnFollowFansInfoService.updatePostTime();
        return Result.ok("互关用户最新发布时间更新完成");
    }

    @ApiOperation(value = "取消没有文章的,并删除", nickname = "取消没有文章的,并删除")
    @GetMapping("/deleteNoArticle")
    public Result deleteNoArticle() {
        csdnFollowFansInfoService.deleteNoArticle();
        return Result.ok("取消没有文章的,并删除完成");
    }


    @ApiOperation(value = "获取通知发红包的用户", nickname = "获取通知发红包的用户")
    @GetMapping("/noticeUsers")
    public Result noticeUsers() {
        return Result.ok(csdnFollowFansInfoService.noticeUsers(10));
    }
}