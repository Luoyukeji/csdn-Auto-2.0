package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BlinkInfoResponse implements Serializable {
    private String msg;
    private Integer code;
    private BlinkInfoData data;

    @Data
    public static class BlinkInfoData implements Serializable {
        private Next next;
        private String officialMark;
        private String vipUrl;
        private String publishDate;
        private Link link;
        private String official;
        private String likeCount;
        private Integer source;
        private Object video;
        private Integer type;
        private String readCount;
        private String createdAt;
        private Integer activityId;
        private TextInfo textInfo;
        private String nickname;
        private Integer id;
        private String text;
        private String forwardCount;
        private Boolean vip;
        private Object vote;
        private Object checkRedPacket;
        private Boolean userAttention;
        private Boolean like;
        private String activityName;
        private String avatar;
        private Object locInfo;
        private Integer commentCount;
        private String vipIcon;
        private List<PicList> picList;
        private String background;
        private String hotComment;
        private String username;
        private Integer status;

        @Data
        public static class Next implements Serializable {
            private Integer id;
            private String text;
            private String url;
        }

        @Data
        public static class Link implements Serializable {
            private Object link;
            private Object linkType;
            private Object pic;
            private Object title;
            private Object redPacketOrderNo;
            private Object desc;

        }

        @Data
        public static class TextInfo implements Serializable {
            private Boolean forwardBlink;
            private Integer masterId;
            private Object masterBlink;
            private Object forwardBlinkStatus;

        }

        @Data
        public static class PicList implements Serializable {
            private String thumbnail;
            private Integer width;
            private Object cdnHost;
            private String type;
            private Object waterMark;
            private String url;
            private Integer height;
            private String object;
        }
    }
}