package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class GetMyAmountResponse implements Serializable {
    private Integer code;
    private GetMyAmountData data;
    private String message;
    private Object msg;

    @Data
    public static class GetMyAmountData implements Serializable {
        private RedPacketInfo redPacketInfo;
        private String redPacketStatus;
        private CurrentUserReceivedInfo currentUserReceivedInfo;
        private List<CurrentUserReceivedInfo> receivedInfos;
        private String myReceiveMoney;
        private Integer receiveAmount;
        private Double receiveMoney;
        private Object receives;
        private Object redPacket;
        private Integer sendAmount;
        private Object sendAvatar;
        private Object status;
        private Object totalMoney;

        @Data
        public static class RedPacketInfo implements Serializable {
            private String resourceId;
            private String creatorUserName;
            private BigDecimal totalMoney;
            private Object completeTime;
            private Integer receivedAmount;
            private String title;
            private Integer type;
            private Object completeTimeInterval;
            private String creatorNickName;
            private Integer totalAmount;
            private Long expireTime;
            private Long createTime;
            private String creatorAvatar;
            private Double receivedMoney;
        }

        @Data
        public static class CurrentUserReceivedInfo implements Serializable {
            private Long receiveTime;
            private Boolean lucky;
            private String receiverNickName;
            private String receiverAvatar;
            private String receiverUserName;
            private String receivedMoney;
        }
    }
}