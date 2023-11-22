package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteFollowResponse implements Serializable {
    private String msg;
    private Integer total;
    private Integer code;
    private Object data;
}