package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;

@Data
public class ScoreResponse implements Serializable {

    private Integer code;
    private ScoreData data;
    private String message;

    @Data
    public static class ScoreData implements Serializable {
        private String article_id;
        private Integer score;
        private String message;
        private String post_time;
    }
}