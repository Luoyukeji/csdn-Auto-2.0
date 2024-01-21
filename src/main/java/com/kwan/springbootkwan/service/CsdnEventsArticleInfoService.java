package com.kwan.springbootkwan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kwan.springbootkwan.entity.CsdnEventsArticleInfo;

/**
 * csdn2023活动文章详细信息(CsdnEventsArticleInfo)表服务接口
 *
 * @author makejava
 * @since 2024-01-16 16:43:12
 */
public interface CsdnEventsArticleInfoService extends IService<CsdnEventsArticleInfo> {
    /**
     * 根据文章id查询文章
     *
     * @param articleId
     * @return
     */
    CsdnEventsArticleInfo selectByArticleId(Integer articleId);

    /**
     * 保存文章
     */
    void savaArticle();

}

