package com.kwan.springbootkwan.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        try {
            Object createTime = metaObject.getValue(CREATE_TIME);
            if (ObjectUtils.isNull(createTime)) {
                this.setFieldValByName(CREATE_TIME, new Date(), metaObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        try {
            Object updateTime = metaObject.getValue(UPDATE_TIME);
            if (ObjectUtils.isNull(updateTime)) {
                this.setFieldValByName(UPDATE_TIME, new Date(), metaObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}