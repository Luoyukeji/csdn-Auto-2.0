package com.kwan.springbootkwan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnFollowFansInfo;
import com.kwan.springbootkwan.entity.CsdnHistorySession;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.CsdnHistorySessionDTO;
import com.kwan.springbootkwan.entity.query.CsdnHistorySessionQuery;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@Slf4j
@Api(tags = "csdn私信")
@RestController
@RequestMapping("/csdn/message")
public class CsdnMessageController {

    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnMessageService csdnMessageService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnFollowFansInfoService csdnFollowFansInfoService;

    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnHistorySessionQuery query) {
        Page<CsdnHistorySession> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnHistorySession> wrapper = new QueryWrapper<>();
        final String userName = query.getUserName();
        if (StringUtils.isNotEmpty(userName)) {
            wrapper.eq("user_name", userName);
        }
        final String nickName = query.getNickName();
        if (StringUtils.isNotEmpty(nickName)) {
            wrapper.like("nick_name", nickName);
        }
        final Integer hasReplied = query.getHasReplied();
        if (Objects.nonNull(hasReplied)) {
            wrapper.eq("has_replied", hasReplied);
        }
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("update_time");
        return Result.ok(CsdnHistorySessionDTO.Converter.INSTANCE.from(this.csdnMessageService.page(pageParm, wrapper)));
    }

    @ApiOperation(value = "获取私信信息", nickname = "获取私信信息")
    @GetMapping("/acquireMessage")
    public Result acquireMessage() {
        return Result.ok(csdnMessageService.acquireMessage());
    }

    @ApiOperation(value = "处理单人私信三连", nickname = "处理单人私信三连")
    @GetMapping("/dealMessageOne")
    public Result dealMessageOne(@RequestParam("userName") String userName) {
        final CsdnUserInfo userByUserName = csdnUserInfoService.getUserByUserName(userName);
        if (Objects.nonNull(userByUserName)) {
            final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(userByUserName.getNickName(), userName);
            if (Objects.nonNull(articleInfo)) {
                csdnService.tripletByArticle(userByUserName, articleInfo);
                csdnMessageService.dealMessageByUserName(articleInfo);
            }
        }
        return Result.ok("处理单人私信三连完成");
    }


    @ApiOperation(value = "处理私信三连", nickname = "处理私信三连")
    @GetMapping("/dealMessage")
    public Result dealMessage() {
        csdnService.dealTriplet();
        return Result.ok("处理私信三连完成");
    }

    @ApiOperation(value = "回复私信", nickname = "回复私信")
    @GetMapping("/replyMessage")
    public Result replyMessage() {
        String msg = "";
        final List<CsdnFollowFansInfo> all = csdnFollowFansInfoService.allIntercorrelation();
        if (CollectionUtil.isNotEmpty(all)) {
            for (CsdnFollowFansInfo csdnFollowFansInfo : all) {
                String toUsername = csdnFollowFansInfo.getUserName();
                if (StringUtils.isNotEmpty(toUsername)) {
                    Integer messageType = 0;
                    String fromClientType = "WEB";
                    String fromDeviceId = "10_20285116700–1699412958190–182091";
                    String appId = "CSDN-PC";
                    csdnMessageService.replyMessage(toUsername, messageType, msg, fromClientType, fromDeviceId, appId);
                }
            }
        }
        return Result.ok("回复私信完成");
    }

    @ApiOperation(value = "设置私信已读", nickname = "设置私信已读")
    @GetMapping("/messageRead")
    public Result messageRead() {
        String toUsername = "";
        csdnMessageService.messageRead(toUsername);
        return Result.ok("设置私信已读完成");
    }

    @ApiOperation(value = "是否回复过私信", nickname = "是否回复过私信")
    @GetMapping("/haveRepliedMessage")
    public Result haveRepliedMessage() {
        String userName = "";
        final CsdnUserInfo userByUserName = csdnUserInfoService.getUserByUserName(userName);
        if (Objects.nonNull(userByUserName)) {
            String nickName = userByUserName.getNickName();
            final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
            if (Objects.nonNull(articleInfo)) {
                csdnMessageService.haveRepliedMessage(userName, articleInfo.getArticleUrl());
            }
        }
        return Result.ok("是否回复过私信完成");
    }
}