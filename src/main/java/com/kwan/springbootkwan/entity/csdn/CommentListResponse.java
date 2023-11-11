package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.util.List;

@Data
public class CommentListResponse {
    public int code;
    public String message;
    public String traceId;
    public DataInfo data;

    @Data
    public static class DataInfo {
        public int count;
        public int pageCount;
        public int floorCount;
        public int foldCount;
        public List<Comment> list;
    }

    @Data
    public static class Comment {
        public Info info;
        public List<SubComment> sub;
        public String pointCommentId;
    }

    @Data
    public static class Info {
        public int commentId;
        public long articleId;
        public int parentId;
        public String postTime;
        public String content;
        public String userName;
        public int digg;
        public List<String> diggArr;
        public boolean loginUserDigg;
        public String parentUserName;
        public String parentNickName;
        public String avatar;
        public String nickName;
        public String dateFormat;
        public int years;
        public boolean vip;
        public String vipIcon;
        public String vipUrl;
        public boolean companyBlog;
        public String companyBlogIcon;
        public boolean flag;
        public String flagIcon;
        public String levelIcon;
        public boolean isTop;
        public boolean isBlack;
        public String region;
        public String orderNo;
        public Object redEnvelopeInfo;
        public Object gptInfo;
        public CommentFromTypeResult commentFromTypeResult;
    }

    @Data
    public static class CommentFromTypeResult {
        public int index;
        public String key;
        public String title;
    }

    @Data
    public static class SubComment {
        public int commentId;
        public long articleId;
        public int parentId;
        public String postTime;
        public String content;
        public String userName;
        public int digg;
        public List<String> diggArr;
        public boolean loginUserDigg;
        public String parentUserName;
        public String parentNickName;
        public String avatar;
        public String nickName;
        public String dateFormat;
        public int years;
        public boolean vip;
        public String vipIcon;
        public String vipUrl;
        public boolean companyBlog;
        public String companyBlogIcon;
        public boolean flag;
        public String flagIcon;
        public String levelIcon;
        public boolean isTop;
        public boolean isBlack;
        public String region;
        public String orderNo;
        public Object redEnvelopeInfo;
        public Object gptInfo;
        public CommentFromTypeResult commentFromTypeResult;
    }
}

