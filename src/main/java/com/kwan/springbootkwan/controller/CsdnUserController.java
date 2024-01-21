package com.kwan.springbootkwan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.CsdnUserInfoDTO;
import com.kwan.springbootkwan.entity.query.CsdnUserInfoQuery;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
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
@Api(tags = "csdn每日三连监控")
@RestController
@RequestMapping("/csdn/user")
public class CsdnUserController {
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;

    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnUserInfoQuery query) {
        final Integer userWeight = query.getUserWeight();
        final String nickName = query.getNickName();
        final String userName = query.getUserName();
        final String articleType = query.getArticleType();
        final Integer likeStatus = query.getLikeStatus();
        final Integer collectStatus = query.getCollectStatus();
        final Integer commentStatus = query.getCommentStatus();
        Page<CsdnUserInfo> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(userName)) {
            wrapper.eq("user_name", userName);
        }
        if (StringUtils.isNotEmpty(articleType)) {
            wrapper.eq("article_type", articleType);
        }
        if (Objects.nonNull(userWeight)) {
            wrapper.eq("user_weight", userWeight);
        }
        if (Objects.nonNull(likeStatus)) {
            wrapper.eq("like_status", likeStatus);
        }
        if (Objects.nonNull(collectStatus)) {
            wrapper.eq("collect_status", collectStatus);
        }
        if (Objects.nonNull(commentStatus)) {
            wrapper.eq("comment_status", commentStatus);
        }
        if (StringUtils.isNotEmpty(nickName)) {
            wrapper.like("nick_name", nickName);
        }
        wrapper.orderByDesc("update_time");
        return Result.ok(CsdnUserInfoDTO.Converter.INSTANCE.from(this.csdnUserInfoService.page(pageParm, wrapper)));
    }

    @ApiOperation(value = "新增用户", nickname = "新增用户")
    @PostMapping("/add")
    public Result add(@RequestBody CsdnUserInfoQuery addInfo) {
        csdnUserInfoService.add(addInfo);
        return Result.ok();
    }

    @ApiOperation(value = "更新用户", nickname = "更新用户")
    @PostMapping("/update")
    public Result update(@RequestBody CsdnUserInfoQuery query) {
        CsdnUserInfo csdnUserInfo = new CsdnUserInfo();
        csdnUserInfo.setId(query.getId());
        csdnUserInfo.setUserName(query.getUserName());
        csdnUserInfo.setNickName(query.getNickName());
        csdnUserInfo.setUserWeight(query.getUserWeight());
        csdnUserInfo.setUserHomeUrl(query.getUserHomeUrl());
        return Result.ok(this.csdnUserInfoService.updateById(csdnUserInfo));
    }

    @ApiOperation(value = "删除用户", nickname = "删除用户")
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        CsdnUserInfo csdnUserInfo = new CsdnUserInfo();
        csdnUserInfo.setIsDelete(1);
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        return Result.ok(this.csdnUserInfoService.update(csdnUserInfo, wrapper));
    }

    @ApiOperation(value = "修正用户的点赞评论状态", nickname = "修正用户的点赞评论状态")
    @GetMapping("/fixUserLikesStatus")
    public Result fixUserLikesStatus() {
        LambdaQueryWrapper<CsdnUserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(QueryWrapper -> QueryWrapper.eq(
                                CsdnUserInfo::getLikeStatus, 0)
                        .or().eq(CsdnUserInfo::getLikeStatus, 2)
                        .or().eq(CsdnUserInfo::getCommentStatus, 0)
                        .or().eq(CsdnUserInfo::getCommentStatus, 2)
                        .or().eq(CsdnUserInfo::getCommentStatus, 3)
                        .or().eq(CsdnUserInfo::getCommentStatus, 4)
                        .or().eq(CsdnUserInfo::getCommentStatus, 5))
                .eq(CsdnUserInfo::getIsDelete, 0);
        final List<CsdnUserInfo> list = csdnUserInfoService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnUserInfo csdnUserInfo : list) {
                final String nickName = csdnUserInfo.getNickName();
                final String userName = csdnUserInfo.getUserName();
                final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
                if (Objects.nonNull(articleInfo)) {
                    csdnUserInfo.setArticleType(CommonConstant.BlogType.BLOG);
                    csdnUserInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
                    csdnUserInfo.setCollectStatus(CommentStatus.UN_PROCESSED.getCode());
                    csdnUserInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
                    csdnUserInfoService.updateById(csdnUserInfo);
                }
            }
        }
        return Result.ok("修正用户的点赞评论状态完成");
    }

    @ApiOperation(value = "重置指定人员新博客状态", nickname = "重置指定人员新博客状态")
    @GetMapping("/resetCsdnUserInfo")
    public Result resetCsdnUserInfo(@Param("username") String username) {
        CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
        if (Objects.nonNull(csdnUserInfo)) {
            final String nickName = csdnUserInfo.getNickName();
            final String userName = csdnUserInfo.getUserName();
            final CsdnArticleInfo articleInfo = csdnArticleInfoService.getArticle(nickName, userName);
            if (Objects.nonNull(articleInfo)) {
                csdnUserInfo.setArticleType(CommonConstant.BlogType.BLOG);
                csdnService.tripletByArticle(csdnUserInfo, articleInfo);
                csdnUserInfoService.updateById(csdnUserInfo);
            }
        }
        return Result.ok("重置指定人员新博客状态完成");
    }

    @ApiOperation(value = "重置多个人员新博客", nickname = "重置多个人员新博客")
    @PostMapping("/resetCsdnUserInfo")
    public Result resetCsdnUserInfo(@RequestBody List<String> userNames) {
        if (CollectionUtil.isNotEmpty(userNames)) {
            for (String userName : userNames) {
                resetCsdnUserInfo(userName);
            }
        }
        return Result.ok("重置多个人员新博客状态完成");
    }

    @ApiOperation(value = "给指定人员添加10篇博客", nickname = "给指定人员添加10篇博客")
    @GetMapping("/add10Blog")
    public Result add10Blog(@Param("username") String username) {
        final CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(username);
        if (Objects.nonNull(csdnUserInfo)) {
            String nickName = csdnUserInfo.getUserName();
            String userName = csdnUserInfo.getNickName();
            csdnArticleInfoService.getArticles10(nickName, userName);
        }
        return Result.ok("给指定人员添加10篇博客完成");
    }
}