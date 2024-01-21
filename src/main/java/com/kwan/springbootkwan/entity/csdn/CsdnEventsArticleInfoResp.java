package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CsdnEventsArticleInfoResp implements Serializable {
    private String traceId;
    private Integer code;
    private EventsArticleInfoData data;
    private String message;

    @Data
    public static class EventsArticleInfoData implements Serializable {
        private String owner;
        private List<ArticleInfo> articleInfo;
        private Integer articleNum;
        private String banner;
        private Boolean simple;
        private List<ActiveInfo> activeInfo;
        private String title;
        private String userUrl;

        @Data
        public static class ArticleInfo implements Serializable {
            private String postTime;
            private Boolean top;
            private Integer articleId;
            private Integer diggCount;
            private Integer viewCount;
            private String title;
            private String authorNickname;
            private String url;
            private Integer offsetId;
            private String userUrl;
            private String desc;
            private Integer commentCount;
        }

        @Data
        public static class ActiveInfo implements Serializable {
            private String name;
            private Integer index;
            private String id;
            private String content;
        }
    }
}