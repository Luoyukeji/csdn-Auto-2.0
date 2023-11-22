package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class DeleteFollowQuery {
    private String username;
    private String detailSourceName;
    private String follow;
    private String fromType;
    private String source;
}