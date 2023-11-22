package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class DeleteQuery {
    private String articleId;
    private boolean deep;
}