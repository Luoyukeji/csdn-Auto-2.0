package com.kwan.springbootkwan.entity.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("csdn抢红包参数类")
public class ReceiveRedPacketQuery {

    @ApiModelProperty("红包订单")
    private String orderNo;
    @ApiModelProperty("用户名称")
    private String username;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}