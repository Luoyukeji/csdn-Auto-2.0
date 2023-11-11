--删除数据库
DROP
DATABASE IF EXISTS kwan;
--创建数据库
CREATE
DATABASE kwan;
--切换数据库
USE
kwan;

-- kwan.algorithmic_problem definition
DROP TABLE IF EXISTS `algorithmic_problem1`;
CREATE TABLE `algorithmic_problem`
(
    `id`                   int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `question_name`        varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `question_type`        int NOT NULL                                                  DEFAULT '0' COMMENT '0:''全部'';\n1: ''基础知识'';\n2: ''集合'';\n3: ''JVM'';\n4: ''并发编程'';\n5:''MySql'';\n6: ''Redis'';\n7: ''中间件'';\n8: ''Spring'';\n9: ''微服务'';\n10:''分布式'';\n11:''项目'';\n12:''算法'';\n99: ''其他'';',
    `degree_of_importance` int NOT NULL                                                  DEFAULT '0' COMMENT '1~10的分值',
    `degree_of_difficulty` int NOT NULL                                                  DEFAULT '1' COMMENT '1:简单;2:中等;3:困难',
    `difficulty_of_score`  int NOT NULL                                                  DEFAULT '0' COMMENT '困难指数',
    `leetcode_number`      int                                                           DEFAULT NULL COMMENT '力扣的问题号',
    `leetcode_link`        varchar(200)                                                  DEFAULT NULL COMMENT '力扣的问题号',
    `create_time`          timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`            tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY                    `interview_question_question_IDX` (`question_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='算法题';


-- kwan.aphorism_poetry definition
DROP TABLE IF EXISTS `aphorism_poetry`;
CREATE TABLE `aphorism_poetry`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `poetry_text` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '诗词内容',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `interview_question_question_IDX` (`poetry_text`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='名言警句';


-- kwan.chatbot definition
DROP TABLE IF EXISTS `chatbot`;
CREATE TABLE `chatbot`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT,
    `question`    text,
    `response`    text,
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='聊天机器人记录表';


-- kwan.csdn_article_info definition
DROP TABLE IF EXISTS `csdn_article_info`;
CREATE TABLE `csdn_article_info`
(
    `id`                  int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `article_id`          varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文章id',
    `article_url`         varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文章URL',
    `article_score`       tinyint                                                       DEFAULT '0' COMMENT '文章分数',
    `article_title`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文章标题',
    `article_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '文章描述',
    `user_name`           varchar(100)                                                  DEFAULT NULL COMMENT '用户名称',
    `nick_name`           varchar(100)                                                  DEFAULT NULL COMMENT '用户昵称',
    `like_status`         tinyint(1) NOT NULL DEFAULT '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `collect_status`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `is_myself`           tinyint(1) DEFAULT '0' COMMENT '是否是自己的文章,默认0不是,1是',
    `create_time`         timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`           tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_user_name` (`article_id`),
    KEY                   `interview_question_question_IDX` (`article_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='csdn文章信息';


-- kwan.csdn_history_session definition
DROP TABLE IF EXISTS `csdn_history_session`;
CREATE TABLE `csdn_history_session`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_name`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'CSDN用户名称',
    `nick_name`   varchar(100)                                                  DEFAULT NULL COMMENT 'CSDN用户昵称',
    `content`     text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '回复内容',
    `message_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '私信地址',
    `has_replied` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0未回复,1已回复',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_user_name` (`user_name`),
    KEY           `interview_question_question_IDX` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='csdn私信用户管理';


-- kwan.csdn_triplet_day_info definition
DROP TABLE IF EXISTS `csdn_triplet_day_info`;
CREATE TABLE `csdn_triplet_day_info`
(
    `id`             int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `triplet_date`   date         DEFAULT NULL COMMENT '三连日期',
    `like_num`       int NOT NULL DEFAULT '0' COMMENT '点赞数量',
    `collect_num`    int NOT NULL DEFAULT '0' COMMENT '收藏数量',
    `comment_num`    int NOT NULL DEFAULT '0' COMMENT '评论数量',
    `like_status`    tinyint(1) NOT NULL DEFAULT '9' COMMENT '点赞状态:\n0:未处理;\n1:已经点过赞;\n2:点赞已满;\n3:取消点赞;\n9:点赞成功;',
    `collect_status` tinyint(1) NOT NULL DEFAULT '9' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status` tinyint(1) NOT NULL DEFAULT '9' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `create_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_user_name` (`triplet_date`),
    KEY              `interview_question_question_IDX` (`triplet_date`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='每日三连监控';


-- kwan.csdn_user_info definition
DROP TABLE IF EXISTS `csdn_user_info`;
CREATE TABLE `csdn_user_info`
(
    `id`             int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `user_name`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `nick_name`      varchar(200)                                                  DEFAULT NULL COMMENT 'CSDN用户名称',
    `like_status`    tinyint(1) NOT NULL DEFAULT '0' COMMENT '点赞状态:\n0:未处理;\n1:已经点过赞;\n2:点赞已满;\n3:取消点赞;\n9:点赞成功;',
    `collect_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '收藏状态:\n0:未处理;\n1:已经收藏过;\n2:收藏已满;\n3:参数缺失;\n9:收藏成功;',
    `comment_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '评论状态:\n0:未处理;\n1:已经评论过;\n2:评论已满;\n3:禁言;\n4:评论太快;\n5:评论已经到了49条;\n8:其他错误;\n9:评论成功;',
    `user_weight`    tinyint NOT NULL                                              DEFAULT '0' COMMENT '用户权重',
    `user_home_url`  varchar(100)                                                  DEFAULT NULL COMMENT '用户主页',
    `article_type`   varchar(10)                                                   DEFAULT 'blog' COMMENT '文章类型',
    `create_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`      tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_user_name` (`user_name`),
    KEY              `interview_question_question_IDX` (`user_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='csdn用户信息';


-- kwan.department definition
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`
(
    `id`         int                                                           NOT NULL AUTO_INCREMENT,
    `dept_name`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    `parent_id`  int                                                           DEFAULT NULL,
    `remark`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    `created_at` datetime                                                      DEFAULT NULL,
    `deleted`    tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    KEY          `department_parent_id_IDX` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门';


-- kwan.dictionary_management definition
DROP TABLE IF EXISTS `dictionary_management`;
CREATE TABLE `dictionary_management`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `dict_type`   int NOT NULL                                                 DEFAULT '0' COMMENT '字典类型',
    `code`        int NOT NULL                                                 DEFAULT '0' COMMENT '字典code',
    `name`        varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '字典名称',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY           `interview_question_question_IDX` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典表';


-- kwan.interview_question definition
DROP TABLE IF EXISTS `interview_question`;
CREATE TABLE `interview_question`
(
    `id`            int unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
    `question`      varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
    `response`      text COMMENT '问题回答',
    `question_type` int NOT NULL                                                  DEFAULT '0' COMMENT '0:''全部'';\n1: ''基础知识'';\n2: ''集合'';\n3: ''JVM'';\n4: ''并发编程'';\n5:''MySql'';\n6: ''Redis'';\n7: ''中间件'';\n8: ''Spring'';\n9: ''微服务'';\n10:''分布式'';\n11:''项目'';\n12:''算法'';\n99: ''其他'';',
    `create_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `is_delete`     tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除,0未删除,1已删除',
    PRIMARY KEY (`id`) USING BTREE,
    KEY             `interview_question_question_IDX` (`question`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='面试题';


-- kwan.pic_info definition
DROP TABLE IF EXISTS `pic_info`;
CREATE TABLE `pic_info`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT,
    `pic_name`    varchar(100) NOT NULL COMMENT '图片名称',
    `pic_url`     varchar(255) NOT NULL COMMENT '图片地址',
    `type`        tinyint(1) NOT NULL DEFAULT '0' COMMENT '图片类型,0:表示宝宝图片,1:学习图片,2:风景,3:美女,99:其他',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `is_delete`   tinyint(1) NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='图片信息表';