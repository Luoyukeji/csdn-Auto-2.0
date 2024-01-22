package com.kwan.springbootkwan.entity.csdn;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CsdnUserSearch implements Serializable {

    private Boolean show_live_status;
    private Object is_navword_result;
    private Object tag_lists;
    private Object ab_test_ext;
    private Integer js_insert_count;
    private Object make_flag;
    private String query_rewrite;
    private Object insertQuickQuestion;
    private Object split_words;
    private String is_contain_baidu;
    private Object is_csdn_url;
    private Object is_navword;
    private Boolean navword_scene;
    private Integer total;
    private Object experiment;
    private Object ad_list;
    private Boolean sensitive_code;
    private List<CsdnUserSearchInner> result_vos;
    private Boolean isc;
    private Integer total_page;
    private Boolean js_insert_first;
    private Object register;
    private Object ask_invite;
    private Object top_list;

    @Data
    public static class CsdnUserSearchInner implements Serializable {
        private String birthday;
        private String translatedcount;
        private String gender;
        private String isCourseLecturer;
        private String industry;
        private String type;
        private String userid;
        private String point;
        private Boolean company_blog;
        private Integer total;
        private Object report_data;
        private Boolean eventClick;
        private String picAdress;
        private String nickname;
        private String user_url;
        private String originalcount;
        private String rank;
        private String viewCount;
        private String email;
        private String follow_flag;
        private String length_statis;
        private String ops_request_misc;
        private String isexpert;
        private String diggcount;
        private String index;
        private String isFollowed;
        private String url;
        private String realname;
        private String popu;
        private String utm_term;
        private String commentcount;
        private String user_pic;
        private String repostcount;
        private String style;
        private String abstract1;
        private String position;
        private String biz_id;
        private String region;
        private String topiccount;
        private String strategy;
        private String request_id;
        private String url_location;
        private String username;
        private Boolean eventView;
        private Object tags;
        private Object utm_source;


        private String ext_27;
        private String digg;
        private String language;
        private String pic;
        private String body;
        private String author_label;
        private String score;
        private String view;
        private String id;
        private String view_num;
        private String create_time;
        private Boolean is_new;
        private String author;
        private String vip_view_auth;
        private String ext_15;
        private String ext_17;
        private Object search_tag;
        private String ext_23;
        private String ext_20;
        private String ext_21;
        private String fetch_field_type;
        private String articleid;
        private String description;
        private String created_at;
        private String title;
        private String ext_1;
        private String ext_10;
        private String digest;
        private String ext_6;
        private String author_space;
        private String create_time_str;
        private String so_type;
        private String comment;
        private Integer locationNum;
        private Object collections;
        private Object article_sco;
        private Object ext_13;
    }
}