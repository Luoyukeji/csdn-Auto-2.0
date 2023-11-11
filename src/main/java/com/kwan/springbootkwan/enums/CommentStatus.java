package com.kwan.springbootkwan.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CommentStatus {
    /**
     * 未处理
     */
    UN_PROCESSED(0, "未处理"),
    /**
     * 已经评论过
     */
    HAVE_ALREADY_COMMENT(1, "已经评论过"),
    /**
     * 评论已满
     */
    COMMENT_IS_FULL(2, "评论已满"),
    /**
     * 禁言
     */
    RESTRICTED_COMMENTS(3, "禁言"),
    /**
     * 评论太快
     */
    COMMENT_TOO_FAST(4, "评论太快"),
    /**
     * 评论已经到了49条
     */
    COMMENT_NUM_49(5, "当日评论已经到了48条"),
    /**
     * 其他错误
     */
    OTHER_ERRORS(8, "其他错误"),
    /**
     * 评论成功
     */
    COMMENT_SUCCESSFUL(9, "评论成功");

    private Integer code;
    private String name;

    CommentStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}