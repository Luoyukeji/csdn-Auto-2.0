package com.kwan.springbootkwan.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwan.springbootkwan.entity.CsdnEventsArticleInfo;
import com.kwan.springbootkwan.entity.csdn.CsdnEventsArticleInfoResp;
import com.kwan.springbootkwan.mapper.CsdnEventsArticleInfoMapper;
import com.kwan.springbootkwan.service.CsdnEventsArticleInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * csdn2023活动文章详细信息(CsdnEventsArticleInfo)表服务实现类
 *
 * @author makejava
 * @since 2024-01-16 16:43:12
 */
@Service
public class CsdnEventsArticleInfoServiceImpl extends ServiceImpl<CsdnEventsArticleInfoMapper, CsdnEventsArticleInfo> implements CsdnEventsArticleInfoService {

    @Value("${csdn.cookie}")
    private String csdnCookie;

    @Override
    public CsdnEventsArticleInfo selectByArticleId(Integer articleId) {
        QueryWrapper<CsdnEventsArticleInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", 0);
        wrapper.eq("article_id", articleId);
        return this.getOne(wrapper);
    }

    @Override
    public void savaArticle() {
        try {
            String url = "https://blog.csdn.net/phoenix/web/v1/WriteArticleActive/page-info?id=10645";
            HttpResponse response = HttpUtil.createGet(url)
                    .header("Cookie", csdnCookie)
                    .form("id", 10645)
                    .execute();
            final String body = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            CsdnEventsArticleInfoResp csdnEventsArticleInfoResp;
            Integer offsetId = null;
            csdnEventsArticleInfoResp = objectMapper.readValue(body, CsdnEventsArticleInfoResp.class);
            final CsdnEventsArticleInfoResp.EventsArticleInfoData data = csdnEventsArticleInfoResp.getData();
            if (Objects.nonNull(data)) {
                final List<CsdnEventsArticleInfoResp.EventsArticleInfoData.ArticleInfo> articleInfo = data.getArticleInfo();
                if (CollectionUtil.isNotEmpty(articleInfo)) {
                    for (CsdnEventsArticleInfoResp.EventsArticleInfoData.ArticleInfo info : articleInfo) {
                        this.saveInfo(info);
                        offsetId = info.getOffsetId();
                    }
                    while (Objects.nonNull(offsetId)) {
                        offsetId = getOffsetId(offsetId);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 持久化
     *
     * @param info
     */
    private void saveInfo(CsdnEventsArticleInfoResp.EventsArticleInfoData.ArticleInfo info) {
        final Integer articleId = info.getArticleId();
        CsdnEventsArticleInfo csdnEvent = this.selectByArticleId(articleId);
        if (Objects.isNull(csdnEvent)) {
            csdnEvent = new CsdnEventsArticleInfo();
            BeanUtils.copyProperties(info, csdnEvent);
            csdnEvent.setAuthorNickName(info.getAuthorNickname());
            csdnEvent.setDescInfo(info.getDesc());
            this.save(csdnEvent);
        } else {
            csdnEvent.setViewCount(info.getViewCount());
            this.updateById(csdnEvent);
        }
    }

    /**
     * 递归调用
     *
     * @param offsetId
     * @return
     */
    private Integer getOffsetId(Integer offsetId) {
        try {
            String url = "https://blog.csdn.net/phoenix/web/v1/WriteArticleActive/page-info?id=10645&offsetId=" + offsetId;
            HttpResponse response = HttpUtil.createGet(url)
                    .header("Cookie", csdnCookie)
                    .form("id", 10645)
                    .form("offsetId", offsetId)
                    .execute();
            final String body = response.body();
            ObjectMapper objectMapper = new ObjectMapper();
            CsdnEventsArticleInfoResp csdnEventsArticleInfoResp;
            csdnEventsArticleInfoResp = objectMapper.readValue(body, CsdnEventsArticleInfoResp.class);
            final CsdnEventsArticleInfoResp.EventsArticleInfoData data = csdnEventsArticleInfoResp.getData();
            if (Objects.nonNull(data)) {
                final List<CsdnEventsArticleInfoResp.EventsArticleInfoData.ArticleInfo> articleInfo = data.getArticleInfo();
                if (CollectionUtil.isNotEmpty(articleInfo)) {
                    for (CsdnEventsArticleInfoResp.EventsArticleInfoData.ArticleInfo info : articleInfo) {
                        this.saveInfo(info);
                        offsetId = info.getOffsetId();
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return offsetId;
    }
}

