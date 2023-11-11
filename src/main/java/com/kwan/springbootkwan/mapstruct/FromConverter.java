package com.kwan.springbootkwan.mapstruct;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.springbootkwan.entity.PageBean;

import java.util.List;

public interface FromConverter<T, FROM> {

    T from(FROM from);

    List<T> from(List<FROM> list);

    default PageBean<T> from(PageBean<FROM> page) {
        PageBean<T> result = new PageBean<>();
        result.setTotalElements(page.getTotalElements());
        result.setNumber(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setNumberOfElements(page.getNumberOfElements());
        result.setContent(this.from(page.getContent()));
        return result;
    }

    default PageBean<T> from(IPage<FROM> page) {
        PageBean<T> result = new PageBean<>();
        result.setTotalElements((int) page.getTotal());
        result.setNumber((int) page.getCurrent());
        result.setSize((int) page.getSize());
        result.setTotalPages((int) page.getPages());
        result.setNumberOfElements(page.getRecords().size());
        result.setContent(this.from(page.getRecords()));
        return result;
    }
}