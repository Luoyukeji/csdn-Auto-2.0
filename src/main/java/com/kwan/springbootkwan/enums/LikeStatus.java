package com.kwan.springbootkwan.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum LikeStatus {
    /**
     * 未处理
     */
    UN_PROCESSED(0, "未处理"),
    /**
     * 已经点过赞
     */
    HAVE_ALREADY_LIKED(1, "已经点过赞"),
    /**
     * 点赞已满
     */
    LIKE_IS_FULL(2, "点赞已满"),
    /**
     * 取消点赞
     */
    CANCEL_LIKES(3, "取消点赞"),
    /**
     * 文章状态不能点赞
     */
    CAN_NOT_LIKES(4, "文章状态不能点赞"),
    /**
     * 其他错误
     */
    OTHER_ERRORS(8, "其他错误"),
    /**
     * 点赞成功
     */
    LIKE_SUCCESSFUL(9, "点赞成功");

    private Integer code;
    private String name;

    LikeStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}