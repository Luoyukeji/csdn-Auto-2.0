package com.kwan.springbootkwan.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.Result;
import com.kwan.springbootkwan.entity.dto.CsdnArticleInfoDTO;
import com.kwan.springbootkwan.entity.query.CsdnArticleInfoQuery;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Slf4j
@Api(tags = "csdn文章管理")
@RestController
@RequestMapping("/csdnArticleInfo")
public class CsdnArticleInfoController {
    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Resource
    private CsdnService csdnService;
    @Resource
    private CsdnUserInfoService csdnUserInfoService;
    @Resource
    private CsdnArticleInfoService csdnArticleInfoService;
    @Resource
    private CsdnTripletDayInfoService csdnTripletDayInfoService;


    @ApiOperation(value = "分页查询所有数据", nickname = "分页查询所有数据")
    @PostMapping("/page")
    public Result selectAll(@RequestBody CsdnArticleInfoQuery query) {
        final String articleId = query.getArticleId();
        final String nickName = query.getNickName();
        final String userName = query.getUserName();
        final Integer likeStatus = query.getLikeStatus();
        final Integer collectStatus = query.getCollectStatus();
        final Integer commentStatus = query.getCommentStatus();
        final Integer scoreStart = query.getArticleScoreStart();
        final Integer scoreEnd = query.getArticleScoreEnd();
        final Integer isMyself = query.getIsMyself();
        Page<CsdnArticleInfo> pageParm = new Page<>();
        pageParm.setCurrent(query.getPage());
        pageParm.setSize(query.getPageSize());
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        if (StringUtils.isNotEmpty(articleId)) {
            wrapper.eq("article_id", articleId);
        }
        if (StringUtils.isNotEmpty(userName)) {
            wrapper.eq("user_name", userName);
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
        if (Objects.nonNull(scoreStart)) {
            wrapper.ge("article_score", scoreStart);
        }
        if (Objects.nonNull(scoreEnd)) {
            wrapper.le("article_score", scoreEnd);
        }
        if (Objects.nonNull(isMyself) && (isMyself == 0 || isMyself == 1)) {
            wrapper.eq("is_myself", isMyself);
        }
        wrapper.orderByDesc("update_time");
        return Result.ok(CsdnArticleInfoDTO.Converter.INSTANCE.from(this.csdnArticleInfoService.page(pageParm, wrapper)));
    }


    @ApiOperation(value = "新增文章", nickname = "新增文章")
    @PostMapping("/add")
    public Result add(@RequestBody CsdnArticleInfoQuery addInfo) {
        final String nickName = addInfo.getNickName();
        final String userName = addInfo.getUserName();
        final String articleId = addInfo.getArticleId();
        if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(articleId)) {
            CsdnArticleInfo csdnArticleInfo = csdnArticleInfoService.getArticleByArticleId(articleId);
            if (Objects.isNull(csdnArticleInfo)) {
                final List<CsdnArticleInfo> articles10 = csdnArticleInfoService.getArticles10(nickName, userName);
                for (CsdnArticleInfo articleInfo : articles10) {
                    if (StringUtils.equals(articleInfo.getArticleId(), articleId)) {
                        log.info("新增文章添加成功");
                    }
                }
            }
        }
        return Result.ok();
    }

    @ApiOperation(value = "更新文章", nickname = "更新文章")
    @PostMapping("/update")
    public Result update(@RequestBody CsdnArticleInfoQuery query) {
        CsdnArticleInfo csdnUserInfo = new CsdnArticleInfo();
        csdnUserInfo.setId(query.getId());
        csdnUserInfo.setUserName(query.getUserName());
        csdnUserInfo.setNickName(query.getNickName());
        csdnUserInfo.setArticleUrl(query.getArticleUrl());
        csdnUserInfo.setArticleId(query.getArticleId());
        return Result.ok(this.csdnArticleInfoService.updateById(csdnUserInfo));
    }

    @ApiOperation(value = "删除文章--物理删除", nickname = "删除文章--物理删除")
    @GetMapping("/delete")
    public Result delete(@RequestParam("id") Integer id) {
        QueryWrapper<CsdnArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("id", id);
        this.csdnArticleInfoService.remove(wrapper);
        return Result.ok("删除文章成功");
    }

    @ApiOperation(value = "根据用户昵称和用户名查询博客", nickname = "根据用户昵称和用户名查询博客")
    @GetMapping("/searchNickName")
    public Result searchNickName(@Param("nickName") String nickName, @Param("userName") String userName) {
        CsdnArticleInfo csdnArticleInfo = csdnArticleInfoService.getArticle(nickName, userName);
        return Result.ok(csdnArticleInfo);
    }

    @ApiOperation(value = "单篇文章三连", nickname = "单篇文章三连")
    @GetMapping("/triplet")
    public Result triplet(@RequestParam("articleId") Integer articleId) {
        final CsdnArticleInfo csdnArticleInfo = this.csdnArticleInfoService.getArticleByArticleId(articleId.toString());
        if (Objects.nonNull(csdnArticleInfo)) {
            final Integer articleScore = csdnArticleInfo.getArticleScore();
            if (articleScore == 0) {
                csdnArticleInfo.setArticleScore(csdnArticleInfoService.getScore(csdnArticleInfo.getArticleUrl()));
                csdnArticleInfoService.updateById(csdnArticleInfo);
            }
            final Integer isMyself = csdnArticleInfo.getIsMyself();
            if (isMyself == 0) {
                final String userName = csdnArticleInfo.getUserName();
                CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(userName);
                csdnService.tripletByArticle(csdnUserInfo, csdnArticleInfo);
            }
        }
        return Result.ok("单篇文章三连完成");
    }

    @ApiOperation(value = "多条blog三连", nickname = "多条blog三连")
    @PostMapping("/multiTriplet")
    public Result multiTriplet(@RequestBody List<String> articleIds) {
        if (CollectionUtil.isNotEmpty(articleIds)) {
            for (String articleId : articleIds) {
                triplet(Integer.valueOf(articleId));
            }
        }
        return Result.ok("重置多个人员新博客状态完成");
    }

    @ApiOperation(value = "重置文章的质量分", nickname = "重置文章的质量分")
    @GetMapping("/getBlogScore")
    public Result getBlogScore() {
        csdnArticleInfoService.getBlogScore();
        return Result.ok("重置文章的质量分完成");
    }

    @ApiOperation(value = "获取文章的质量分", nickname = "获取文章的质量分")
    @GetMapping("/getScore")
    public Result getScore(@RequestParam("articleUrl") String articleUrl) {
        return Result.ok(csdnArticleInfoService.getScore(articleUrl));
    }

    @ApiOperation(value = "修正文章的点赞评论状态", nickname = "修正文章的点赞评论状态")
    @GetMapping("/fixLikesStatus")
    public Result fixLikesStatus() {
        final CsdnTripletDayInfo csdnTripletDayInfo = csdnTripletDayInfoService.todayInfo();
        LambdaQueryWrapper<CsdnArticleInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(QueryWrapper -> QueryWrapper.eq(
                                CsdnArticleInfo::getLikeStatus, 0)
                        .or().eq(CsdnArticleInfo::getLikeStatus, 2)
                        .or().eq(CsdnArticleInfo::getCommentStatus, 0)
                        .or().eq(CsdnArticleInfo::getCommentStatus, 2)
                        .or().eq(CsdnArticleInfo::getCommentStatus, 3)
                        .or().eq(CsdnArticleInfo::getCommentStatus, 4)
                        .or().eq(CsdnArticleInfo::getCommentStatus, 5))
                .eq(CsdnArticleInfo::getIsDelete, 0);
        final List<CsdnArticleInfo> list = csdnArticleInfoService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnArticleInfo csdnArticleInfo : list) {
                CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(csdnArticleInfo.getUserName());
                csdnArticleInfoService.checkBlogStatus(csdnTripletDayInfo, csdnArticleInfo, csdnUserInfo);
            }
        }
        return Result.ok("修正文章的点赞评论状态完成");
    }

    @ApiOperation(value = "查看某一文章的阅读量", nickname = "查看某一文章的阅读量")
    @GetMapping("/getViewCount")
    public Result getViewCount(@RequestParam("userName") String userName, @RequestParam("articleId") String articleId) {
        return Result.ok(this.csdnArticleInfoService.getViewCount(userName, articleId));
    }

    @ApiOperation(value = "同步本人博客到表中", nickname = "同步本人博客到表中")
    @GetMapping("/syncMyBlog")
    public Result syncMyBlog() {
        this.csdnArticleInfoService.syncMyBlog();
        return Result.ok("同步本人博客到表中成功");
    }

    @ApiOperation(value = "删除低分文章", nickname = "删除低分文章")
    @GetMapping("/deletaLowBlog")
    public Result deletaLowBlog() {
        this.csdnArticleInfoService.deleteLowBlog();
        return Result.ok("删除低分文章成功");
    }
}