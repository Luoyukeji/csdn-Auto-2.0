package com.kwan.springbootkwan.entity.csdn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class RedPackageResponse implements Serializable {
    private String msg;
    private Integer code;
    private RedPackageData data;
    private String message;

    @Data
    public static class RedPackageData implements Serializable {
        private Integer total;
        private List<RedPackageInfo> list;
        private Integer type;


        public static class RedPackageInfo implements Serializable {
            private Integer tabId;
            private String itemType;
            private Boolean allowPost;
            private CommunityUser communityUser;
            private String communityHomePageUrl;
            private ResourceExt resourceExt;
            private String mediaType;
            private Content content;
            private String subResourceType;
            private Integer communityType;
            private String showType;
            private String communityName;
            private Object submitHistory;
            private Content.CheckRedPacket checkRedPacket;
            private String avatarUrl;
            private String nickName;
            private String articleId;
            private String description;
            private String diggCount;
            private String recommend;
            private String title;
            private String type;
            private String url;
            private String commentCount;
            private String postTime;
            private Boolean isTop;
            private List<Object> picList;
            private String domain;
            private Integer editType;
            private String viewCount;
            private String channelId;
            private Integer commentAuth;
            private Integer status;
            private Object imgData;
            private Object msg;
            private Object officialMark;
            private Object vipUrl;
            private Object link;
            private Object publishDate;
            private Object official;
            private Object likeCount;
            private Object source;
            private Object readCount;
            private Object activityId;
            private Object createdAt;
            private Object video;
            private Object textInfo;
            @JsonProperty("nickname")
            private String nickname;
            @JsonProperty("userName")
            private String userName;
            @JsonProperty("username")
            private String username;
            private Object id;
            private Object text;
            private Object forwardCount;
            private Object vip;
            private Object userAttention;
            private Object like;
            private Object activityName;
            private Object avatar;
            private Object vote;
            private Object vipIcon;
            private Object background;
            private Object hotComment;
            private Object locInfo;

            public Object getLocInfo() {
                return locInfo;
            }

            public void setLocInfo(Object locInfo) {
                this.locInfo = locInfo;
            }

            public Integer getTabId() {
                return tabId;
            }

            public void setTabId(Integer tabId) {
                this.tabId = tabId;
            }

            public String getItemType() {
                return itemType;
            }

            public void setItemType(String itemType) {
                this.itemType = itemType;
            }

            public Boolean getAllowPost() {
                return allowPost;
            }

            public void setAllowPost(Boolean allowPost) {
                this.allowPost = allowPost;
            }

            public CommunityUser getCommunityUser() {
                return communityUser;
            }

            public void setCommunityUser(CommunityUser communityUser) {
                this.communityUser = communityUser;
            }

            public String getCommunityHomePageUrl() {
                return communityHomePageUrl;
            }

            public void setCommunityHomePageUrl(String communityHomePageUrl) {
                this.communityHomePageUrl = communityHomePageUrl;
            }

            public ResourceExt getResourceExt() {
                return resourceExt;
            }

            public void setResourceExt(ResourceExt resourceExt) {
                this.resourceExt = resourceExt;
            }

            public String getMediaType() {
                return mediaType;
            }

            public void setMediaType(String mediaType) {
                this.mediaType = mediaType;
            }

            public Content getContent() {
                return content;
            }

            public void setContent(Content content) {
                this.content = content;
            }

            public String getSubResourceType() {
                return subResourceType;
            }

            public void setSubResourceType(String subResourceType) {
                this.subResourceType = subResourceType;
            }

            public Integer getCommunityType() {
                return communityType;
            }

            public void setCommunityType(Integer communityType) {
                this.communityType = communityType;
            }

            public String getShowType() {
                return showType;
            }

            public void setShowType(String showType) {
                this.showType = showType;
            }

            public String getCommunityName() {
                return communityName;
            }

            public void setCommunityName(String communityName) {
                this.communityName = communityName;
            }

            public Object getSubmitHistory() {
                return submitHistory;
            }

            public void setSubmitHistory(Object submitHistory) {
                this.submitHistory = submitHistory;
            }

            public Content.CheckRedPacket getCheckRedPacket() {
                return checkRedPacket;
            }

            public void setCheckRedPacket(Content.CheckRedPacket checkRedPacket) {
                this.checkRedPacket = checkRedPacket;
            }

            public String getAvatarUrl() {
                return avatarUrl;
            }

            public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getArticleId() {
                return articleId;
            }

            public void setArticleId(String articleId) {
                this.articleId = articleId;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDiggCount() {
                return diggCount;
            }

            public void setDiggCount(String diggCount) {
                this.diggCount = diggCount;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getCommentCount() {
                return commentCount;
            }

            public void setCommentCount(String commentCount) {
                this.commentCount = commentCount;
            }

            public String getPostTime() {
                return postTime;
            }

            public void setPostTime(String postTime) {
                this.postTime = postTime;
            }

            public Boolean getIsTop() {
                return isTop;
            }

            public void setIsTop(Boolean top) {
                isTop = top;
            }

            public List<Object> getPicList() {
                return picList;
            }

            public void setPicList(List<Object> picList) {
                this.picList = picList;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public Integer getEditType() {
                return editType;
            }

            public void setEditType(Integer editType) {
                this.editType = editType;
            }

            public String getViewCount() {
                return viewCount;
            }

            public void setViewCount(String viewCount) {
                this.viewCount = viewCount;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public Integer getCommentAuth() {
                return commentAuth;
            }

            public void setCommentAuth(Integer commentAuth) {
                this.commentAuth = commentAuth;
            }

            public Integer getStatus() {
                return status;
            }

            public void setStatus(Integer status) {
                this.status = status;
            }

            public Object getImgData() {
                return imgData;
            }

            public void setImgData(Object imgData) {
                this.imgData = imgData;
            }

            public Object getMsg() {
                return msg;
            }

            public void setMsg(Object msg) {
                this.msg = msg;
            }

            public Object getOfficialMark() {
                return officialMark;
            }

            public void setOfficialMark(Object officialMark) {
                this.officialMark = officialMark;
            }

            public Object getVipUrl() {
                return vipUrl;
            }

            public void setVipUrl(Object vipUrl) {
                this.vipUrl = vipUrl;
            }

            public Object getLink() {
                return link;
            }

            public void setLink(Object link) {
                this.link = link;
            }

            public Object getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(Object publishDate) {
                this.publishDate = publishDate;
            }

            public Object getOfficial() {
                return official;
            }

            public void setOfficial(Object official) {
                this.official = official;
            }

            public Object getLikeCount() {
                return likeCount;
            }

            public void setLikeCount(Object likeCount) {
                this.likeCount = likeCount;
            }

            public Object getSource() {
                return source;
            }

            public void setSource(Object source) {
                this.source = source;
            }

            public Object getReadCount() {
                return readCount;
            }

            public void setReadCount(Object readCount) {
                this.readCount = readCount;
            }

            public Object getActivityId() {
                return activityId;
            }

            public void setActivityId(Object activityId) {
                this.activityId = activityId;
            }

            public Object getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(Object createdAt) {
                this.createdAt = createdAt;
            }

            public Object getVideo() {
                return video;
            }

            public void setVideo(Object video) {
                this.video = video;
            }

            public Object getTextInfo() {
                return textInfo;
            }

            public void setTextInfo(Object textInfo) {
                this.textInfo = textInfo;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public Object getId() {
                return id;
            }

            public void setId(Object id) {
                this.id = id;
            }

            public Object getText() {
                return text;
            }

            public void setText(Object text) {
                this.text = text;
            }

            public Object getForwardCount() {
                return forwardCount;
            }

            public void setForwardCount(Object forwardCount) {
                this.forwardCount = forwardCount;
            }

            public Object getVip() {
                return vip;
            }

            public void setVip(Object vip) {
                this.vip = vip;
            }

            public Object getUserAttention() {
                return userAttention;
            }

            public void setUserAttention(Object userAttention) {
                this.userAttention = userAttention;
            }

            public Object getLike() {
                return like;
            }

            public void setLike(Object like) {
                this.like = like;
            }

            public Object getActivityName() {
                return activityName;
            }

            public void setActivityName(Object activityName) {
                this.activityName = activityName;
            }

            public Object getAvatar() {
                return avatar;
            }

            public void setAvatar(Object avatar) {
                this.avatar = avatar;
            }

            public Object getVote() {
                return vote;
            }

            public void setVote(Object vote) {
                this.vote = vote;
            }

            public Object getVipIcon() {
                return vipIcon;
            }

            public void setVipIcon(Object vipIcon) {
                this.vipIcon = vipIcon;
            }

            public Object getBackground() {
                return background;
            }

            public void setBackground(Object background) {
                this.background = background;
            }

            public Object getHotComment() {
                return hotComment;
            }

            public void setHotComment(Object hotComment) {
                this.hotComment = hotComment;
            }

            @Data
            public static class CheckRedPacketInfo implements Serializable {
                private String readStatus;
                private String orderNo;
                private String credPacketAmount;
                private Integer credPacketType;
                private String credPacketTitle;
                private String status;
                private Object msg;
            }

            @Data
            public static class CommunityUser implements Serializable {
                private Object honoraryName;
                private Integer honoraryId;
                private Integer roleId;
                private String communityNickname;
                private String communitySignature;
                private String roleName;
                private Integer roleType;
                private String userName;
                private Integer roleStatus;
            }

            @Data
            public static class ResourceExt implements Serializable {
                private Object careerUserUrl;
                private Object sponsorInfo;
                private Object marketQuestionUrl;
                private Object marketActivityUrl;
                private Object voteId;
                private Object marketActivityId;
                private Object careerUserName;
                private Object syncAsk;
                private Object marketQuestionId;
            }

            @Data
            public static class Content implements Serializable {
                private Boolean insertFirst;
                private String coverImg;
                private String publishDate;
                private Boolean digg;
                private String dependId;
                private Employee employee;
                private String type;
                private String cateName;
                private Object dependSubType;
                private Boolean hit;
                private Double avgScore;
                private String id;
                private Object text;
                private Integer resourceSource;
                private List<UserCertification> userCertification;
                private Object taskStatus;
                private Object taskExpired;
                private CheckRedPacket checkRedPacket;
                private Object videoInfo;
                private Integer commentCount;
                private Integer cateId;
                private String shareUrl;
                private Integer status;
                private Employee student;
                private Integer contentId;
                private String description;
                private Integer best;
                private Boolean favoriteStatus;
                private Object content;
                private Object pictures;
                private String topicTitle;
                private Object likeInfo;
                private Object taskType;
                private Boolean expired;
                private Integer top;
                private Object videoUrl;
                private Integer diggNum;
                private String nickname;
                private Integer viewCount;
                private Object defaultScore;
                private Object linkInfo;
                private Object videoPlayLength;
                private String lastReplyDate;
                private Object mdContent;
                private String updateTime;
                private String avatar;
                private String resourceUsername;
                private Double totalScore;
                private String url;
                private Integer taskCate;
                private String createTime;
                private Integer taskAward;
                private Boolean syncAsk;
                private Integer favoriteCount;
                private String username;
                private Object liveInfo;

                @Data
                public static class Employee implements Serializable {
                    private Boolean isCertification;
                    private String org;
                    private String bala;
                }

                @Data
                public static class UserCertification implements Serializable {
                    private String icon;
                    private String description;
                    private String type;
                }

                @Data
                public static class CheckRedPacket implements Serializable {
                    private String msg;
                    private String readStatus;
                    private String contractType;
                    private Integer credPacketTitleType;
                    private Integer postId;
                    private ContractConfig contractConfig;
                    private BigDecimal credPacketAmount;
                    private Integer credPacketType;
                    private Object internalAttribute;
                    private String credPacketTitle;
                    private Object status;
                    private String orderNo;

                    @Data
                    public static class ContractConfig implements Serializable {
                        private Integer resourceId;
                        private Integer communityId;
                        private Object limitDays;
                        private Object wordNums;
                        private Object titlePrefix;
                    }
                }
            }
        }
    }
}