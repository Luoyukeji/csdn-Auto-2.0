package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MessageHistoryListResponse implements Serializable {

    private String code;
    private List<MessageHistoryListData> data;
    private String message;
    private Boolean status;

    @Data
    public static class MessageHistoryListData implements Serializable {
        private Integer messageStatus;
        private Integer messageType;
        private String messageBody;
        private Long createTime;
        private String toUsername;
        private Long updateTime;
        private String id;
        private String fromUsername;
    }
}