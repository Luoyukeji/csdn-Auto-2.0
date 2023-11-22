package com.kwan.springbootkwan.schedule;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kwan.springbootkwan.constant.CommonConstant;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
import com.kwan.springbootkwan.service.CsdnMessageService;
import com.kwan.springbootkwan.service.CsdnService;
import com.kwan.springbootkwan.service.CsdnTripletDayInfoService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsdnSchedule {

    @Value("${csdn.self_user_name}")
    private String selfUserName;
    @Autowired
    private CsdnService csdnService;
    @Autowired
    private CsdnMessageService csdnMessageService;
    @Autowired
    private CsdnUserInfoService csdnUserInfoService;
    @Autowired
    private CsdnAutoReplyService csdnAutoReplyService;
    @Autowired
    private CsdnArticleInfoService csdnArticleInfoService;
    @Autowired
    private CsdnLikeCollectService csdnLikeCollectService;
    @Autowired
    private CsdnTripletDayInfoService csdnTripletDayInfoService;

    @ApiOperation(value = "自动三连+自动回复评论", nickname = "自动三连+自动回复评论")
    @Scheduled(cron = "0 0 6,12,23 * * ?")
    public void allTripletAndCommentSelf() {
        log.info("allTripletAndCommentSelf task is running ... ...");
        csdnAutoReplyService.commentSelf();
        csdnLikeCollectService.dealLikeCollect(csdnLikeCollectService.acquireLikeCollect());
        csdnMessageService.dealMessage(csdnMessageService.acquireMessage());
        log.info("allTripletAndCommentSelf task is finish ... ...");
    }

    @ApiOperation(value = "检查用户是否发布新的文章", nickname = "检查用户是否发布新的文章")
    @Scheduled(cron = "0 0/30 8,11,15,21,23 * * ?")
    public void resetAllCurrentStatus() {
        log.info("resetAllCurrentStatus task is running ... ...");
        //用户可能有新的文章,更新用户的状态
        final List<CsdnUserInfo> allUser = csdnUserInfoService.getAllUser();
        final CsdnTripletDayInfo csdnTripletDayInfo = csdnTripletDayInfoService.todayInfo();
        if (CollectionUtil.isNotEmpty(allUser)) {
            for (CsdnUserInfo csdnUserInfo : allUser) {
                final String userName = csdnUserInfo.getUserName();
                final List<BusinessInfoResponse.ArticleData.Article> articles = csdnArticleInfoService.getArticles10(userName);
                if (CollectionUtil.isNotEmpty(articles)) {
                    final BusinessInfoResponse.ArticleData.Article article = articles.get(0);
                    csdnUserInfoService.checkUserStatus(csdnTripletDayInfo, csdnUserInfo, article);
                }
            }
        }
        log.info("resetAllCurrentStatus task is finish ... ...");
    }

    @ApiOperation(value = "新的一天初始化", nickname = "新的一天初始化")
    @Scheduled(cron = "0 3 0 * * ?")
    public void resetTripletDayInfo() {
        log.info("resetTripletDayInfo task is running ... ...");
        CsdnTripletDayInfo csdnTripletDayInfo = csdnTripletDayInfoService.todayInfo();
        //重置用户状态
        this.resetAllCurrentStatus();
        //重置文章状态
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
        List<CsdnArticleInfo> list = csdnArticleInfoService.list(wrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnArticleInfo csdnArticleInfo : list) {
                CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(csdnArticleInfo.getUserName());
                csdnArticleInfoService.checkBlogStatus(csdnTripletDayInfo, csdnArticleInfo, csdnUserInfo);
            }
        }
        List<BusinessInfoResponse.ArticleData.Article> articles = csdnArticleInfoService.getArticles100(selfUserName);
        articles = articles.stream().filter(x -> x.getType().equals(CommonConstant.ARTICLE_TYPE_BLOG)).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(articles)) {
            csdnService.autoAddView(articles);
        }
        log.info("resetTripletDayInfo task is finish ... ...");
    }

    @ApiOperation(value = "自动评论(因为有一分钟之内只能发3条的限制,所以这里指定时间执行," +
            "设置在下午执行主要是为了把每天仅有的48个评论留给评论自己的老铁和私信自己的老铁," +
            "当然这里的私信人也必须是白名单的人,默认人是不三连的)"
            , nickname = "自动评论(因为有一分钟之内只能发3条的限制,所以这里指定时间执行)")
    @Scheduled(cron = "0 0/2 06,21,23 * * ?")
    public void frequentlyComment() {
        log.info("frequentlyComment task is running ... ...");
        final List<CsdnUserInfo> csdnUserInfos = csdnUserInfoService.waitForCommentsUser();
        if (CollectionUtil.isNotEmpty(csdnUserInfos)) {
            for (CsdnUserInfo csdnUserInfo : csdnUserInfos) {
                csdnService.singleArticle(csdnUserInfo);
            }
        }
        log.info("frequentlyComment task is finish ... ...");
    }
}