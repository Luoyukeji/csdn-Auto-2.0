package com.kwan.springbootkwan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
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

@Slf4j
@RestController
@Api(tags = "csdn三连用户管理")
@RequestMapping("/csdn")
public class CsdnController {

    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Value("${csdn.self_user_nick_name}")
    private String selfNickName;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnAutoReplyService csdnAutoReplyService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnLikeCollectService csdnLikeCollectService;
    @Autowired
    private CsdnFollowFansInfoService csdnFollowFansInfoService;

    @ApiOperation(value = "自刷流量", nickname = "自刷流量")
    @GetMapping("/autoAddView")
    public Result autoAddView() {
        final List<CsdnArticleInfo> articles100 = csdnArticleInfoService.getArticles100(selfNickName, selfUserName);
        if (CollectionUtil.isNotEmpty(articles100)) {
            csdnService.autoAddView(articles100);
        }
        return Result.ok("自刷流量完成");
    }

    @ApiOperation(value = "单人三连", nickname = "单人三连")
    @GetMapping("/singleTriplet")
    public Result singleTriplet(@Param("username") String username) {
        CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
        if (Objects.nonNull(csdnUserInfo)) {
            final String nickName = csdnUserInfo.getNickName();
            final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, username);
            if (Objects.nonNull(articleInfo)) {
                csdnUserInfo.setArticleType(CommonConstant.BlogType.BLOG);
                csdnService.tripletByArticle(csdnUserInfo, articleInfo);
            }
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
        final List<CsdnUserInfo> allUser = csdnUserInfoService.allUser();
        csdnService.extractSingleArticle(allUser);
        return Result.ok("全员三连完成");
    }

    @ApiOperation(value = "自动回复", nickname = "自动回复")
    @GetMapping("/autoReply")
    public Result autoReply() {
        csdnAutoReplyService.commentSelf();
        return Result.ok("自动回复完成");
    }


    @ApiOperation(value = "给指定文章刷流量", nickname = "给指定文章刷流量")
    @GetMapping("/autoAddViewByUrl")
    public Result autoAddViewByUrl(@RequestParam("url") String url) {
        if (StringUtils.isNotEmpty(url)) {
            csdnService.autoAddViewByUrl(url);
        }
        return Result.ok("给指定文章刷流量完成");
    }

    @ApiOperation(value = "每日任务", nickname = "每日任务")
    @GetMapping("/dailyTask")
    public Result dailyTask() {
        csdnFollowFansInfoService.saveFans();
        csdnFollowFansInfoService.saveFollow();
        csdnFollowFansInfoService.deleteFollow();
        csdnFollowFansInfoService.deleteNoArticle();
        csdnAutoReplyService.commentSelf();
        csdnService.dealTriplet();
        csdnLikeCollectService.dealLikeCollect(csdnLikeCollectService.acquireLikeCollect());
        return Result.ok("每日任务完成");
    }
}
