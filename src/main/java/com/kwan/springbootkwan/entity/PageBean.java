package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.util.List;

public class PageBean<T> implements Serializable {
    private static final long serialVersionUID = 8656597559014685635L;
    private long totalElements;
    private List<T> content;
    private long number;
    private long size;
    private long totalPages;
    private long numberOfElements;

    public PageBean() {
    }

    public PageBean(List<T> list) {
        if (list != null) {
            this.number = 1L;
            this.size = (long) list.size();
            this.totalElements = (long) list.size();
            this.totalPages = 1L;
            this.content = list;
            this.numberOfElements = (long) list.size();
        }
    }

    public PageBean(IPage<T> page) {
        if (page != null) {
            this.number = page.getCurrent();
            this.size = page.getSize();
            this.totalElements = page.getTotal();
            this.totalPages = page.getPages();
            this.content = page.getRecords();
            if (this.content != null && !this.content.isEmpty()) {
                this.numberOfElements = (long) this.content.size();
            } else {
                this.numberOfElements = 0L;
            }
        }
    }

    public List<T> getContent() {
        return this.content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public long getNumber() {
        return this.number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getNumberOfElements() {
        return this.numberOfElements;
    }

    public void setNumberOfElements(long numberOfElements) {
        this.numberOfElements = numberOfElements;
    }
}