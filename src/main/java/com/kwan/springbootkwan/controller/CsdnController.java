package com.kwan.springbootkwan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "csdn三连用户管理")
@RequestMapping("/csdn")
public class CsdnController {
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnAutoReplyService csdnAutoReplyService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;

    @ApiOperation(value = "自刷流量", nickname = "自刷流量")
    @GetMapping("/autoAddView")
    public Result autoAddView() {
        List<BusinessInfoResponse.ArticleData.Article> articles = csdnArticleInfoService.getArticles100(selfUserName);
        if (CollectionUtil.isNotEmpty(articles)) {
            articles = articles.stream().filter(x -> x.getType().equals(CommonConstant.ARTICLE_TYPE_BLOG)).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(articles)) {
                csdnService.autoAddView(articles);
            }
        }
        return Result.ok("自刷流量完成");
    }

    @ApiOperation(value = "给指定文章刷流量", nickname = "给指定文章刷流量")
    @GetMapping("/autoAddViewByUrl")
    public Result autoAddViewByUrl(@RequestParam("url") String url) {
        if (StringUtils.isNotEmpty(url)) {
            csdnService.autoAddViewByUrl(url);
        }
        return Result.ok("给指定文章刷流量完成");
    }

    @ApiOperation(value = "单人三连", nickname = "单人三连")
    @GetMapping("/singleTriplet")
    public Result singleTriplet(@Param("username") String username) {
        CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
        if (Objects.nonNull(csdnUserInfo)) {
            csdnService.singleArticle(csdnUserInfo);
        }
        return Result.ok("单人三连完成");
    }

    @ApiOperation(value = "根据用户名多人三连", nickname = "根据用户名多人三连")
    @PostMapping("/multiTriplet")
    public Result multiTriplet(@RequestBody List<String> userNames) {
        if (CollectionUtil.isNotEmpty(userNames)) {
            for (String userName : userNames) {
                singleTriplet(userName);
            }
        }
        return Result.ok("根据用户名多人三连完成");
    }

    @ApiOperation(value = "全员三连", nickname = "全员三连")
    @GetMapping("/allTriplet")
    public Result allTriplet() {
        csdnService.allTriplet();
        return Result.ok("全员三连完成");
    }

    @ApiOperation(value = "自动回复", nickname = "自动回复")
    @GetMapping("/autoReply")
    public Result autoReply() {
        csdnAutoReplyService.commentSelf();
        return Result.ok("自动回复完成");
    }
}
