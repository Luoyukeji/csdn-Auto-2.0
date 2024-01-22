package com.kwan.springbootkwan.schedule;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kwan.springbootkwan.entity.CsdnArticleInfo;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.service.CsdnAccountManagementService;
import com.kwan.springbootkwan.service.CsdnArticleInfoService;
import com.kwan.springbootkwan.service.CsdnAutoReplyService;
import com.kwan.springbootkwan.service.CsdnFollowFansInfoService;
import com.kwan.springbootkwan.service.CsdnLikeCollectService;
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
import java.util.Objects;

@Slf4j
@Component
public class CsdnSchedule {
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
    @Autowired
    private CsdnTripletDayInfoService csdnTripletDayInfoService;
    @Autowired
    private CsdnAccountManagementService csdnAccountManagementService;

    @ApiOperation(value = "自动回复评论+自动给给我点赞的的三连并私信+处理私信", nickname = "自动回复评论+自动给给我点赞的的三连并私信+处理私信")
    @Scheduled(cron = "0 0 8,12,21 * * ?")
    public void allTripletAndCommentSelf() {
        csdnAutoReplyService.commentSelf();
        csdnService.dealTriplet();
        csdnLikeCollectService.dealLikeCollect(csdnLikeCollectService.acquireLikeCollect());
    }

    @ApiOperation(value = "检查用户是否发布新的文章", nickname = "检查用户是否发布新的文章")
    @Scheduled(cron = "0 0/30 6,23 * * ?")
    public void resetAllCurrentStatus() {
        csdnUserInfoService.updateUserInfo();
    }

    @ApiOperation(value = "新的一天初始化", nickname = "新的一天初始化")
    @Scheduled(cron = "0 3 0 * * ?")
    public void resetTripletDayInfo() {
        CsdnTripletDayInfo csdnTripletDayInfo = csdnTripletDayInfoService.todayInfo();
        QueryWrapper<CsdnArticleInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wrapper -> wrapper.eq("like_status", 0)
                .or().eq("like_status", 2)
        ).and(wrapper -> wrapper.eq("comment_status", 0)
                .or().eq("comment_status", 2)
                .or().eq("comment_status", 3)
                .or().eq("comment_status", 4)
                .or().eq("comment_status", 5)
        ).eq("is_delete", 0);
        List<CsdnArticleInfo> list = csdnArticleInfoService.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            for (CsdnArticleInfo csdnArticleInfo : list) {
                CsdnUserInfo csdnUserInfo = csdnUserInfoService.getUserByUserName(csdnArticleInfo.getUserName());
                if (Objects.nonNull(csdnUserInfo)) {
                    csdnArticleInfoService.checkBlogStatus(csdnTripletDayInfo, csdnArticleInfo, csdnUserInfo);
                }
            }
        }
        final List<CsdnArticleInfo> articles100 = csdnArticleInfoService.getArticles100(selfNickName, selfUserName);
        if (CollectionUtil.isNotEmpty(articles100)) {
            csdnService.autoAddView(articles100);
        }
        csdnFollowFansInfoService.saveFans();
        csdnFollowFansInfoService.saveFollow();
        csdnFollowFansInfoService.deleteFollow();
        csdnFollowFansInfoService.deleteNoArticle();
        csdnAccountManagementService.addAccountInfo();
    }

    @ApiOperation(value = "自动评论-因为有一分钟之内只能发3条的限制,所以这里指定时间执行," +
            "设置在下午执行主要是为了把每天仅有的48个评论留给评论自己的老铁和私信自己的老铁," +
            "当然这里的私信人也必须是白名单里面的人,陌生人是不能三连的", nickname = "自动评论")
    @Scheduled(cron = "0 0/1 06,08,09,10,11,14,16,17,21,22,23 * * ?")
    public void frequentlyComment() {
        final List<CsdnUserInfo> csdnUserInfos = csdnUserInfoService.waitForCommentsUser();
        log.info("待处理集合数量={}", csdnUserInfos.size());
        csdnService.extractSingleArticle(csdnUserInfos);
    }
}