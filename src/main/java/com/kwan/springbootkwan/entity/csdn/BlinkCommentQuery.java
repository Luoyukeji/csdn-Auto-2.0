package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class BlinkCommentQuery {
    private Integer blinkId;
    private String conformText;
    private String content;
    private Boolean isCheck;
    private String masterId;
    private String parentId;
    private String refs;
    private String replyNickname;
    private String replyUsername;
    private String text;
    private BlinkInfoResponse.BlinkInfoData textInfo;
}