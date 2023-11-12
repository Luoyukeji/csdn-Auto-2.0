package com.kwan.springbootkwan.enums;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CollectStatus {
    /**
     * 未处理
     */
    UN_PROCESSED(0, "未处理"),
    /**
     * 已经收藏过
     */
    HAVE_ALREADY_COLLECT(1, "已经收藏过"),
    /**
     * 收藏已满
     */
    COLLECT_IS_FULL(2, "收藏已满"),
    /**
     * 参数缺失
     */
    MISSING_PARAMETER(3, "参数缺失"),
    /**
     * 收藏夹不存在
     */
    FOLDER_NOT_EXIST(4, "收藏夹不存在"),
    /**
     * 其他错误
     */
    OTHER_ERRORS(8, "其他错误"),
    /**
     * 收藏成功
     */
    COLLECT_SUCCESSFUL(9, "收藏成功");

    private Integer code;
    private String name;

    CollectStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}