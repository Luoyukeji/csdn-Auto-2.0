package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class CollectResponse {
    public int code;
    public String msg;
    public String total;
    public Object data;
}