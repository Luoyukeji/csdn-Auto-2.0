package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class ReceiveTopicRedPacketQuery {
    private String resourceId;
    private Integer communityId;
    private Integer postId;
}