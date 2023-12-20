package com.kwan.springbootkwan.entity;

import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

public class BeanCopierUtils {
    private static Map<String, BeanCopier> beanCopierCacheMap = new HashMap();

    public BeanCopierUtils() {
    }

    public static void copyProperties(Object source, Object target) {
        String cacheKey = source.getClass().toString() + target.getClass().toString();
        BeanCopier beanCopier;
        if (!beanCopierCacheMap.containsKey(cacheKey)) {
            synchronized (BeanCopierUtils.class) {
                if (!beanCopierCacheMap.containsKey(cacheKey)) {
                    beanCopier = BeanCopier.create(source.getClass(), target.getClass(), false);
                    beanCopierCacheMap.put(cacheKey, beanCopier);
                } else {
                    beanCopier = beanCopierCacheMap.get(cacheKey);
                }
            }
        } else {
            beanCopier = beanCopierCacheMap.get(cacheKey);
        }
        beanCopier.copy(source, target, null);
    }
}