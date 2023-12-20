package com.kwan.springbootkwan.entity.csdn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class CurrentAmount implements Serializable {
    private Integer code;
    private CurrentAmountData data;
    private String message;

    @Data
    public static class CurrentAmountData implements Serializable {
        @JsonProperty("cBeans")
        private CurrentAmountDetail cBeans;
        private CurrentAmountDetail balance;
        @JsonProperty("enterprise")
        private CurrentAmountDetail enterprise;
        private CurrentAmountDetail cb;

        @Data
        public static class CurrentAmountDetail implements Serializable {
            private BigDecimal expireAmount;
            private BigDecimal total;
            private BigDecimal normalAmount;
        }
    }
}
