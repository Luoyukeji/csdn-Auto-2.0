package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReceiveRedPacketResponse implements Serializable {
    private Integer code;
    private ReceiveRedPacketData data;
    private String message;

    @Data
    public static class ReceiveRedPacketData implements Serializable {
        private Double redPacketMoney;
    }
}