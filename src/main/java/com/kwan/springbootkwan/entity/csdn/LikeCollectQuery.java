package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

@Data
public class LikeCollectQuery {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer type;
}