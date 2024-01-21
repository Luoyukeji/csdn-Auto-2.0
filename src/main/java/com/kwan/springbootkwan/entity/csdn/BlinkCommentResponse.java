package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlinkCommentResponse implements Serializable {
    private int code;
    private Object data;
    private String msg;
}
