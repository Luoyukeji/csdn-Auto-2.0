package com.kwan.springbootkwan.entity.query;

import lombok.Data;

@Data
public class AphorismPoetryQuery {
    private Integer id;
    private String poetryText;
    private Integer addType;
}