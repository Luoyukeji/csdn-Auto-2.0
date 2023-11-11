package com.kwan.springbootkwan.mapstruct;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kwan.springbootkwan.entity.PageBean;

import java.util.List;

public interface ToConverter<T, TO> {
    TO to(T t);

    List<TO> to(List<T> list);

    default PageBean<TO> to(PageBean<T> page) {
        PageBean<TO> result = new PageBean<>();
        result.setTotalElements(page.getTotalElements());
        result.setNumber(page.getNumber());
        result.setSize(page.getSize());
        result.setTotalPages(page.getTotalPages());
        result.setNumberOfElements(page.getNumberOfElements());
        result.setContent(this.to(page.getContent()));
        return result;
    }

    default PageBean<TO> to(IPage<T> page) {
        PageBean<TO> result = new PageBean<>();
        result.setTotalElements((int) page.getTotal());
        result.setNumber((int) page.getCurrent());
        result.setSize((int) page.getSize());
        result.setTotalPages((int) page.getPages());
        result.setNumberOfElements(page.getRecords().size());
        result.setContent(this.to(page.getRecords()));
        return result;
    }
}