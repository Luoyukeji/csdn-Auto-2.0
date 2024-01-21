package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlinkLikeResponse implements Serializable {
    private int code;
    private Boolean data;
    private String msg;
}
