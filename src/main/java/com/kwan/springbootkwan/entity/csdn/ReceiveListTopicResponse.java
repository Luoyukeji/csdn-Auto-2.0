package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class ReceiveListTopicResponse implements Serializable {
    private String msg;
    private Integer code;
    private ReceiveListData data;
    private String message;

    @Data
    public static class ReceiveListData implements Serializable {
        private RedPacket redPacket;
        private Integer receiveAmount;
        private String myReceiveMoney;
        private String sendAvatar;
        private BigDecimal totalMoney;
        private Integer sendAmount;
        private String receiveMoney;
        private List<ReceivesInfo> receives;
        private Object status;

        @Data
        public static class RedPacket implements Serializable {
            private String resourceId;
            private Long expireTime;
            private Object showCompleteTime;
            private Long createTime;
            private String name;
            private String nickname;
            private Object completeTime;
            private Integer redPacketType;
        }

        @Data
        public static class ReceivesInfo implements Serializable {
            private String receiveTime;
            private Boolean lucky;
            private Double money;
            private Long createTime;
            private String nickname;
            private String avatar;
            private String username;
        }
    }
}