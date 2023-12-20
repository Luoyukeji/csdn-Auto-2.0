package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CsdnAccountInfoResponse implements Serializable {
    private Integer code;
    private CsdnAccountInfoData data;
    private String message;

    @Data
    public static class CsdnAccountInfoData implements Serializable {
        private List<DetailData> data;
        private Integer totalPages;
        private Integer maxPageSize;
        private Integer pageSize;
        private Integer totalCount;
        private Integer pageNum;

        @Data
        public static class DetailData implements Serializable {
            private String amount;
            private String product;
            private Integer code;
            private String orderNo;
            private String expireTime;
            private Object refundOrderNo;
            private Integer operateType;
            private String description;
            private String time;
            private String productName;
        }
    }
}