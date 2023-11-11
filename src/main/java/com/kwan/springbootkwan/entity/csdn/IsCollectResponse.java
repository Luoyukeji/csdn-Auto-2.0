package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class IsCollectResponse {
    public int code;
    public String message;
    public String traceId;
    public CollectDataDetail data;

    @Data
    public static class CollectDataDetail {
        public String tips;
        public boolean status;
    }
}