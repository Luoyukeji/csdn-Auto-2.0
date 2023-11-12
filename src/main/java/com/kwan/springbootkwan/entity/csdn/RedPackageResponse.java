package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
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
        private List<RedPackageList> list;
        private Integer type;

        @Data
        public static class RedPackageList implements Serializable {
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
                private java.util.List<UserCertification> userCertification;
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
                    private Integer credPacketAmount;
                    private Integer credPacketType;
                    private Object internalAttribute;
                    private String credPacketTitle;
                    private Integer status;

                    @Data
                    public static class ContractConfig implements Serializable {
                        private Integer resourceId;
                        private Integer communityId;
                    }
                }
            }
        }
    }
}