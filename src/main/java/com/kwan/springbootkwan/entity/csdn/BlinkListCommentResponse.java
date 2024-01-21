package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlinkListCommentResponse implements Serializable {
    private String msg;
    private Integer code;
    private List<BlinkListCommentData> data;

    @Data
    public static class BlinkListCommentData implements Serializable {
        private String resourceGroup;
        private String resourceId;
        private Student student;
        private Integer userAudit;
        private FlowerName flowerName;
        private Integer likeCount;
        private Student employee;
        private Title title;
        private String platform;
        private String content;
        private Integer score;
        private Integer top;
        private String fromType;
        private String nickname;
        private Boolean userLike;
        private Integer id;
        private String ext;
        private String resourceOrder;
        private String certificatePic;
        private String bizNo;
        private String orderNo;
        private Integer level;
        private Long updateTime;
        private String avatar;
        private String certificateInfo;
        private Integer parentId;
        private String resourceUser;
        private String replyUsername;
        private String createTime;
        private Boolean isBlack;
        private Boolean anonymous;
        private Integer ifLike;
        private VipUserInfo vipUserInfo;
        private String region;
        private String foldScore;
        private String username;
        private Integer status;

        @Data
        public static class Student implements Serializable {
            private Boolean isCertification;
            private String org;
            private String bala;
        }

        @Data
        public static class FlowerName implements Serializable {
            private String level;
            private String flowerName;
        }

        @Data
        public static class Title implements Serializable {
            private Integer id;
            private Boolean used;
            private String titleUrl;
            private String username;
        }

        @Data
        public static class VipUserInfo implements Serializable {
            private Boolean vipUser;
            private String pic;
            private String url;
        }
    }
}