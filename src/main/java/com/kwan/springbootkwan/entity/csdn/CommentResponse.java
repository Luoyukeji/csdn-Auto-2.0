package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class CommentResponse {
    public int code;
    public String message;
    public String traceId;
    public int data;
}