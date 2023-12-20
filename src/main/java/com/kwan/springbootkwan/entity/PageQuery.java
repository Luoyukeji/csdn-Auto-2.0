package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageQuery extends BaseQuery {
    /**
     * 分页
     *
     * @return
     */
    public Page page() {
        Page pageParam;
        if (super.getPage() == -1) {
            pageParam = new Page<>(1L, Long.MAX_VALUE);
        } else {
            pageParam = new Page<>(super.getPage(), super.getSize());
        }
        return pageParam;
    }
}