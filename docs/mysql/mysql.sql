
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


-- kwan.csdn_triplet_day_info definition

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