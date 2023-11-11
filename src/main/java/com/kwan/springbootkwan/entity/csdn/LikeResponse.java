package com.kwan.springbootkwan.entity.csdn;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LikeResponse {
    public int code;
    public String message;
    public String traceId;
    public LikeDataDetail data;

    @Data
    public static class LikeDataDetail {
        @JsonProperty("like_num")
        public int likeNum;
        public boolean status;
    }
}