package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;

@Data
public class FansResponse implements Serializable {
    private String msg;
    private Integer total;
    private Integer code;
    private FansData data;

    @Data
    public static class FansData implements Serializable {
        private Integer total;
        private Integer fanId;
        private java.util.List<FansListData> list;

        @Data
        public static class FansListData implements Serializable {
            private Object blogExpert;
            private Object certifiInfo;
            private String blogUrl;
            private String nickname;
            private Object icon;
            private String description;
            private Object remark;
            private String avatar;
            private Object type;
            private Object remarkType;
            private String username;
            private Integer relation;
        }
    }
}