-- kwan.algorithmic_problem definition
drop table if exists `algorithmic_problem`;
create table `algorithmic_problem`
(
    `id`                   int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `question_name`        varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null,
    `question_type`        varchar(10) not null                                          default '0' COMMENT '类型',
    `degree_of_importance` int         not null                                          default '0' COMMENT '1~10的分值',
    `degree_of_difficulty` int         not null                                          default '1' COMMENT '1:简单;2:中等;3:困难',
    `difficulty_of_score`  int         not null                                          default '0' COMMENT '困难指数',
    `leetcode_number`      int                                                           default null COMMENT '力扣的问题号',
    `leetcode_link`        varchar(200)                                                  default null COMMENT '力扣的问题号',
    `create_time`          timestamp null default current_timestamp COMMENT '创建时间',
    `is_delete`            tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    KEY                    `interview_question_question_IDX` (`question_name`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='算法题';


-- kwan.aphorism_poetry definition
drop table if exists `aphorism_poetry`;
create table `aphorism_poetry`
(
    `id`          int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `poetry_text` varchar(256) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '诗词内容',
    `create_time` timestamp null default current_timestamp COMMENT '创建时间',
    `is_delete`   tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    KEY           `interview_question_question_IDX` (`poetry_text`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='名言警句';


-- kwan.chatbot definition
drop table if exists `chatbot`;
create table `chatbot`
(
    `id`          int unsigned not null AUTO_INCREMENT,
    `question`    text,
    `response`    text,
    `create_time` timestamp null default current_timestamp,
    `is_delete`   tinyint(1) not null default '0',
    primary key (`id`) using BTREE
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='聊天机器人记录表';


-- kwan.csdn_account_management definition
drop table if exists `csdn_account_management`;
create table `csdn_account_management`
(
    `id`           int unsigned not null AUTO_INCREMENT,
    `amount`       decimal(10, 2) not null COMMENT '金额',
    `product`      varchar(30)    not null COMMENT '金额业务类型',
    `code`         int            not null default '0' COMMENT '业务code,7000:红包收入',
    `order_no`     varchar(30)    not null COMMENT '订单号',
    `expire_time`  varchar(30)             default null COMMENT '过期时间',
    `operate_type` tinyint(1) not null default '1' COMMENT '1收入 2支出',
    `description`  varchar(100)   not null COMMENT '详情',
    `time`         varchar(30)    not null COMMENT '时间',
    `product_name` varchar(30)    not null COMMENT '业务名称',
    `create_time`  timestamp null default current_timestamp COMMENT '创建时间',
    `update_time`  timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`    tinyint(1) not null default '0',
    primary key (`id`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='余额信息表';


-- kwan.csdn_article_info definition
drop table if exists `csdn_article_info`;
create table `csdn_article_info`
(
    `id`                  int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `article_id`          varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '文章id',
    `article_url`         varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '文章URL',
    `article_score`       tinyint                                                       default '0' COMMENT '文章分数',
    `article_title`       varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '文章标题',
    `article_description` text character set utf8mb4 collate utf8mb4_0900_ai_ci COMMENT '文章描述',
    `user_name`           varchar(100)                                                  default null COMMENT '用户名称',
    `nick_name`           varchar(100)                                                  default null COMMENT '用户昵称',
    `like_status`         tinyint(1) not null default '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `collect_status`      tinyint(1) not null default '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status`      tinyint(1) not null default '0' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `is_myself`           tinyint(1) default '0' COMMENT '是否是自己的文章,默认0不是,1是',
    `create_time`         timestamp null default current_timestamp COMMENT '创建时间',
    `update_time`         timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`           tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    unique key `unique_user_name` (`article_id`),
    KEY                   `interview_question_question_IDX` (`article_id`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='csdn文章信息';


-- kwan.csdn_follow_fans_info definition
drop table if exists `csdn_follow_fans_info`;
create table `csdn_follow_fans_info`
(
    `id`            int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `user_name`     varchar(30) character set utf8mb4 collate utf8mb4_0900_ai_ci  default null COMMENT '用户名',
    `nick_name`     varchar(60) character set utf8mb4 collate utf8mb4_0900_ai_ci  default null COMMENT '昵称',
    `blog_url`      varchar(100) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '主页',
    `relation_type` varchar(20) character set utf8mb4 collate utf8mb4_0900_ai_ci  default null COMMENT '关系类型',
    `post_time`     timestamp null default null COMMENT '发布时间',
    `need_notice`   int not null                                                  default '0' COMMENT '需要红包通知',
    `create_time`   timestamp null default current_timestamp COMMENT '创建时间',
    `update_time`   timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`     tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    unique key `unique_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='粉丝关注';
-- kwan.csdn_history_session definition
drop table if exists `csdn_history_session`;
create table `csdn_history_session`
(
    `id`          int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `user_name`   varchar(100) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT 'CSDN用户名称',
    `nick_name`   varchar(100)                                                  default null COMMENT 'CSDN用户昵称',
    `content`     text character set utf8mb4 collate utf8mb4_0900_ai_ci COMMENT '回复内容',
    `message_url` varchar(100) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '私信地址',
    `has_replied` tinyint(1) not null default '0' COMMENT '0未回复,1已回复',
    `create_time` timestamp null default current_timestamp COMMENT '创建时间',
    `update_time` timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`   tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    unique key `unique_user_name` (`user_name`),
    KEY           `interview_question_question_IDX` (`user_name`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='csdn私信用户管理';

-- kwan.csdn_triplet_day_info definition
drop table if exists `csdn_triplet_day_info`;
create table `csdn_triplet_day_info`
(
    `id`             int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `triplet_date`   date                                                         default null COMMENT '三连日期',
    `like_num`       int not null                                                 default '0' COMMENT '点赞数量',
    `collect_num`    int not null                                                 default '0' COMMENT '收藏数量',
    `comment_num`    int not null                                                 default '0' COMMENT '评论数量',
    `week_info`      varchar(10) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '星期',
    `like_status`    tinyint(1) not null default '9' COMMENT '点赞状态:\n0:未处理;\n1:已经点过赞;\n2:点赞已满;\n3:取消点赞;\n9:点赞成功;',
    `collect_status` tinyint(1) not null default '9' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status` tinyint(1) not null default '9' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `create_time`    timestamp null default current_timestamp COMMENT '创建时间',
    `update_time`    timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`      tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    unique key `unique_user_name` (`triplet_date`),
    KEY              `interview_question_question_IDX` (`triplet_date`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='每日三连监控';


-- kwan.csdn_user_info definition
drop table if exists `csdn_user_info`;
create table `csdn_user_info`
(
    `id`             int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `user_name`      varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null,
    `nick_name`      varchar(200)                                                  default null COMMENT 'CSDN用户名称',
    `like_status`    tinyint(1) not null default '0' COMMENT '点赞状态:\n0:未处理;\n1:已经点过赞;\n2:点赞已满;\n3:取消点赞;\n9:点赞成功;',
    `collect_status` tinyint(1) not null default '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status` tinyint(1) not null default '0' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `user_weight`    tinyint not null                                              default '0' COMMENT '用户权重',
    `user_home_url`  varchar(100)                                                  default null COMMENT '用户主页',
    `article_type`   varchar(10)                                                   default 'blog' COMMENT '文章类型',
    `create_time`    timestamp null default current_timestamp COMMENT '创建时间',
    `update_time`    timestamp null default current_timestamp on update current_timestamp COMMENT '更新时间',
    `is_delete`      tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    unique key `unique_user_name` (`user_name`),
    KEY              `interview_question_question_IDX` (`user_name`) using BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='csdn用户信息';


-- kwan.department definition
drop table if exists `department`;
create table `department`
(
    `id`         int                                                           not null AUTO_INCREMENT,
    `dept_name`  varchar(100) character set utf8mb4 collate utf8mb4_unicode_ci not null,
    `parent_id`  int                                                           default null,
    `remark`     varchar(100) character set utf8mb4 collate utf8mb4_unicode_ci default null,
    `created_at` datetime                                                      default null,
    `deleted`    tinyint(1) not null default '0',
    primary key (`id`),
    KEY          `department_parent_id_IDX` (`parent_id`) using BTREE
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_unicode_ci COMMENT='部门';


-- kwan.dictionary_management definition
drop table if exists `dictionary_management`;
create table `dictionary_management`
(
    `id`          int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `dict_type`   int not null                                                 default '0' COMMENT '字典类型',
    `code`        int not null                                                 default '0' COMMENT '字典code',
    `name`        varchar(50) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '字典名称',
    `create_time` timestamp null default current_timestamp COMMENT '创建时间',
    `is_delete`   tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    KEY           `interview_question_question_IDX` (`dict_type`) using BTREE
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='字典表';


-- kwan.interview_question definition
drop table if exists `interview_question`;
create table `interview_question`
(
    `id`            int unsigned not null AUTO_INCREMENT COMMENT '主键id',
    `question`      varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '问题',
    `question_type` varchar(200) character set utf8mb4 collate utf8mb4_0900_ai_ci default null COMMENT '问题类型',
    `response`      text COMMENT '问题回答',
    `create_time`   timestamp null default current_timestamp COMMENT '创建时间',
    `is_delete`     tinyint(1) not null default '0' COMMENT '逻辑删除,0未删除,1已删除',
    primary key (`id`) using BTREE,
    KEY             `interview_question_question_IDX` (`question`) using BTREE
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='面试题';


-- kwan.pic_info definition
drop table if exists `pic_info`;
create table `pic_info`
(
    `id`          int unsigned not null AUTO_INCREMENT,
    `pic_name`    varchar(100) not null COMMENT '图片名称',
    `pic_url`     varchar(255) not null COMMENT '图片地址',
    `type`        tinyint(1) not null default '0' COMMENT '图片类型,0:表示宝宝图片,1:学习图片,2:风景,3:美女,99:其他',
    `create_time` timestamp null default current_timestamp,
    `is_delete`   tinyint(1) not null default '0',
    primary key (`id`) using BTREE
) ENGINE=InnoDB default CHARSET=utf8mb4 collate=utf8mb4_0900_ai_ci ROW_FORMAT=dynamic COMMENT='图片信息表';