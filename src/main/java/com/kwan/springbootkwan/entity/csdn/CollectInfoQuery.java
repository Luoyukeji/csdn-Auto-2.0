package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.util.List;

@Data
public class CollectInfoQuery {
    private Integer sourceId;
    private String fromType;
    private String author;
    private String description;
    private String source;
    private List<Integer> folderIdList;
    private String title;
    private String url;
    private String username;
}