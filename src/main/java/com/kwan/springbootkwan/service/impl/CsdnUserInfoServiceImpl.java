package com.kwan.springbootkwan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kwan.springbootkwan.entity.CsdnTripletDayInfo;
import com.kwan.springbootkwan.entity.CsdnUserInfo;
import com.kwan.springbootkwan.entity.csdn.BusinessInfoResponse;
import com.kwan.springbootkwan.entity.query.CsdnUserInfoQuery;
import com.kwan.springbootkwan.enums.CollectStatus;
import com.kwan.springbootkwan.enums.CommentStatus;
import com.kwan.springbootkwan.enums.LikeStatus;
import com.kwan.springbootkwan.mapper.CsdnUserInfoMapper;
import com.kwan.springbootkwan.service.CsdnCollectService;
import com.kwan.springbootkwan.service.CsdnCommentService;
import com.kwan.springbootkwan.service.CsdnLikeService;
import com.kwan.springbootkwan.service.CsdnUserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsdnUserInfoServiceImpl extends ServiceImpl<CsdnUserInfoMapper, CsdnUserInfo> implements CsdnUserInfoService {

    @Value("${csdn.self_user_name}")
    private String selfUserName;

    @Autowired
    private CsdnLikeService csdnLikeService;
    @Autowired
    private CsdnCollectService csdnCollectService;
    @Autowired
    private CsdnCommentService csdnCommentService;


    @Override
    public CsdnUserInfo getUserByUserName(String username) {
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
//        wrapper.eq("is_delete", 0);删除的人也要能查到,不然会重复加入
        wrapper.eq("user_name", username)
                .last("limit 1");
        return this.getOne(wrapper);
    }

    @Override
    public List<CsdnUserInfo> getAllUser() {
        QueryWrapper<CsdnUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.orderByDesc("user_weight");
        return this.list(wrapper);
    }

    @Override
    public List<CsdnUserInfo> waitForCommentsUser() {
        //未处理-评论已满-禁言-评论太快-评论>49
        LambdaQueryWrapper<CsdnUserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(QueryWrapper -> QueryWrapper.eq(CsdnUserInfo::getCommentStatus, 0)
                .or().eq(CsdnUserInfo::getCommentStatus, 2)
                .or().eq(CsdnUserInfo::getCommentStatus, 3)
                .or().eq(CsdnUserInfo::getCommentStatus, 4)
                .or().eq(CsdnUserInfo::getCommentStatus, 5))
                .eq(CsdnUserInfo::getIsDelete, 0)
                .eq(CsdnUserInfo::getArticleType, "blog")
                .last("order by rand()")
                .last("limit 20")
        ;
        return this.list(wrapper);
    }

    @Override
    public void add(CsdnUserInfoQuery addInfo) {
        final String userName = addInfo.getUserName();
        final Integer addType = addInfo.getAddType();
        if (StringUtils.isNotEmpty(userName)) {
            //批量添加
            if (addType == 1) {
                final String[] split = userName.split("\n");
                for (String str : split) {
                    str = str.trim();
                    if (StringUtils.isNotEmpty(str)) {
                        CsdnUserInfo csdnUserInfo = this.getUserByUserName(str);
                        if (csdnUserInfo == null) {
                            csdnUserInfo = new CsdnUserInfo();
                            BeanUtils.copyProperties(addInfo, csdnUserInfo);
                            csdnUserInfo.setUserName(str);
                            csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + str);
                            this.save(csdnUserInfo);
                        }
                        addInfo.setNickName(csdnUserInfo.getNickName());
                    }
                }
            } else {
                CsdnUserInfo csdnUserInfo = this.getUserByUserName(userName);
                if (csdnUserInfo == null) {
                    csdnUserInfo = new CsdnUserInfo();
                    BeanUtils.copyProperties(addInfo, csdnUserInfo);
                    csdnUserInfo.setUserHomeUrl("https://blog.csdn.net/" + userName);
                    this.save(csdnUserInfo);
                }
                addInfo.setNickName(csdnUserInfo.getNickName());
            }
        }
    }

    @Override
    public void checkUserStatus(CsdnTripletDayInfo csdnTripletDayInfo, CsdnUserInfo csdnUserInfo, BusinessInfoResponse.ArticleData.Article article) {
        final Integer likeStatus = csdnTripletDayInfo.getLikeStatus();
        final Integer commentStatus = csdnTripletDayInfo.getCommentStatus();
        final String type = article.getType();
        if (!StringUtils.equals(type, "blog")) {
            csdnUserInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
            csdnUserInfo.setCollectStatus(CommentStatus.UN_PROCESSED.getCode());
            csdnUserInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
        } else {
            //判断该博客的点赞和评论状态
            final String articleUrl = article.getUrl();
            String articleId = articleUrl.substring(articleUrl.lastIndexOf("/") + 1);
            final Boolean like = csdnLikeService.isLike(articleId, csdnUserInfo);
            //点赞
            if (like) {
                csdnUserInfo.setLikeStatus(LikeStatus.HAVE_ALREADY_LIKED.getCode());
            } else if (LikeStatus.LIKE_IS_FULL.getCode().equals(likeStatus)) {
                csdnUserInfo.setLikeStatus(LikeStatus.LIKE_IS_FULL.getCode());
            } else {
                //如果当天是点赞成功状态,则可以点赞,文章点赞状态是未处理状态
                csdnUserInfo.setLikeStatus(LikeStatus.UN_PROCESSED.getCode());
            }
            //收藏
            final Boolean collect = csdnCollectService.isCollect(articleId, csdnUserInfo);
            if (collect) {
                csdnUserInfo.setCollectStatus(CollectStatus.HAVE_ALREADY_COLLECT.getCode());
            } else if (CollectStatus.COLLECT_IS_FULL.getCode().equals(likeStatus)) {
                csdnUserInfo.setCollectStatus(CollectStatus.COLLECT_IS_FULL.getCode());
            } else {
                csdnUserInfo.setCollectStatus(CollectStatus.UN_PROCESSED.getCode());
            }
            //评论
            final Boolean comment = csdnCommentService.isComment(article, csdnUserInfo);
            if (comment) {
                csdnUserInfo.setCommentStatus(CommentStatus.HAVE_ALREADY_COMMENT.getCode());
            } else if (CommentStatus.COMMENT_IS_FULL.getCode().equals(commentStatus)
                    || CommentStatus.RESTRICTED_COMMENTS.getCode().equals(commentStatus)
                    || CommentStatus.COMMENT_NUM_49.getCode().equals(commentStatus)) {
                csdnUserInfo.setCommentStatus(commentStatus);
            } else {
                csdnUserInfo.setCommentStatus(CommentStatus.UN_PROCESSED.getCode());
            }
        }
        this.updateById(csdnUserInfo);
    }
}