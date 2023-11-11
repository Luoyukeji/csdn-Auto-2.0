package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteResponse implements Serializable {
    private String traceId;
    private Integer code;
    private Boolean data;
    private String message;
}
