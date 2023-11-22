package com.kwan.springbootkwan.entity.csdn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class BusinessInfoResponse {

    private String traceId;
    private Integer code;
    private ArticleData data;
    private String message;

    @Data
    public static class ArticleData {
        public Object total;
        public List<Article> list;

        @Data
        public static class Article {
            public String formatTime;
            public Integer collectCount;
            public String source;
            public String description;
            public Integer diggCount;
            public String type;
            public String title;
            public Boolean hasOriginal;
            public String url;
            public Integer commentCount;
            public String rtype;
            public Object postTime;
            public String editUrl;
            public Object createTime;
            public Integer articleType;
            public List<Object> picList;
            public Integer viewCount;
            public Object expandTitle;
            public Object cover;
            public Object fileType;
            public Object content;
            public Object status;
            public String articleId;
            public Object top;
            public Object forcePlan;
            public Object solve;
            public Object answersCount;
        }
    }

    public static void main(String[] args) {
        String body = " {\"code\":200,\"message\":\"success\",\"traceId\":\"56e59727-b0e5-4017-b613-3bfb9dbee864\",\"data\":{\"list\":[{\"type\":\"blog\",\"formatTime\":\"12 分钟前\",\"title\":\"【实战项目】从0到1实现高并发内存池（上）\",\"description\":\"\u200B本篇文章是实现一个高并发的内存池，他的原型是google的一个开源项目tcmalloc（tcmalloc源码），tcmalloc全称Thread-Caching Malloc，即线程缓存的malloc，是一种用于内存分配和管理的内存分配器（内存池）。这个项目旨在提高多线程应用程序的性能，实现了高效的多线程内存管理。\",\"hasOriginal\":true,\"diggCount\":4,\"commentCount\":3,\"postTime\":1698045098000,\"createTime\":1697553615000,\"url\":\"https://blog.csdn.net/weixin_67596609/article/details/133895730\",\"articleType\":1,\"viewCount\":36,\"picList\":[\"https://img-blog.csdnimg.cn/b7634b29af834f9d88cad6ba1de0258a.png\"],\"editUrl\":\"https://mp.csdn.net/console/editor/html/133895730\",\"collectCount\":3,\"rtype\":\"article\"},{\"type\":\"blink\",\"formatTime\":\"昨天 10:40\",\"title\":\"前几天由于一些原因，没有更新文章。最近要开始持续更新啦！\\nps：想多了全是问题，做多了全是答案！加油！\\n\",\"picList\":[{\"thumbnail\":\"https://userblink.csdnimg.cn/6748783d6e974f5da67346c6b0e9b2f0.png?x-oss-process=image/interlace,1/format,jpg/resize,w_375\",\"url\":\"https://userblink.csdnimg.cn/6748783d6e974f5da67346c6b0e9b2f0.png\"}],\"createTime\":1697942415000,\"url\":\"https://blink.csdn.net/details/1591445\",\"source\":1,\"expandTitle\":null,\"cover\":null,\"rtype\":\"picture\"}],\"total\":null}}";
        ObjectMapper objectMapper = new ObjectMapper();
        BusinessInfoResponse businessInfoResponse = null;
        try {
            businessInfoResponse = objectMapper.readValue(body, BusinessInfoResponse.class);
            System.out.println(businessInfoResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        final ArticleData data = businessInfoResponse.getData();
        final List<ArticleData.Article> list = data.getList();
        final ArticleData.Article article = list.get(0);
        final String type = article.getType();
        final String urlInfo = article.getUrl();
        System.out.println(type);
        System.out.println(urlInfo);
    }
}