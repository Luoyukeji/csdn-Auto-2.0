package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class TopicReceiveResponse implements Serializable {
    private String msg;
    private Integer code;
    private Object data;
    private String message;
}
