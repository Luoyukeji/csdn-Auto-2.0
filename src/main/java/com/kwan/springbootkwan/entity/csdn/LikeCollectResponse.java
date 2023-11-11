package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.util.List;

@Data
public class LikeCollectResponse {
    public String code;
    public String message;
    public boolean status;
    public LikeCollectDataDetail data;

    @Data
    public static class LikeCollectDataDetail {
        public Integer countNum;
        public Object hasUnRead;
        public Integer unReadCount;
        public List<ResultList> resultList;

        @Data
        public static class ResultList {
            public Integer id;
            public String time;
            public Integer status;
            public String username;
            public ContentInfo content;

            @Data
            public static class ContentInfo {
                public String tt;
                public String pd;
                public String avatarUrl;
                public String usernames;
                public String id;
                public String nicknames;
                public String title;
                public String url;
                public Integer taskId;
                public String tc;
                public String username;
                public Object identity;
                public Object nickname;
                public Object isFans;
                public Object reviewContent;
                public Object commentId;
                public Object templateId;
                public Object reviewImage;
                public Object appType;
            }
        }
    }
}