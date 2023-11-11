package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MessageResponse implements Serializable {

    private String code;
    private MessageData data;
    private String message;
    private Boolean status;

    @Data
    public static class MessageData implements Serializable {
        private Object foldSession;
        private List<Sessions> sessions;
        private Boolean allFoldSession;

        @Data
        public static class Sessions implements Serializable {
            private Boolean coupon;
            private Boolean official;
            private Long updateTime;
            private String avatar;
            private String content;
            private Boolean fourteenDay;
            private Integer relation;
            private Boolean fansDigitalShow;
            private Boolean setTop;
            private Integer messageType;
            private Long createTime;
            private String nickname;
            private Integer sessionType;
            private Integer unReadCount;
            private Boolean digitalShow;
            private String username;
            private Boolean hasReplied;
            private Object identity;
        }
    }
}