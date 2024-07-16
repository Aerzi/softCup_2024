-- 创建数据库
CREATE DATABASE IF NOT EXISTS tp_education;

USE tp_education;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int NULL DEFAULT NULL,
  `sex` int NULL DEFAULT NULL,
  `birth_day` datetime NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `role` int NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `image_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `last_active_time` datetime NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_file
-- ----------------------------
DROP TABLE IF EXISTS `t_file`;
CREATE TABLE t_file (
    `id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `extension` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL, -- 文件拓展名
    `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL, -- 用于存储文件类型，例如 "document" 或 "video"
    `file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL, -- 文件在 CDN 上的 URL
    `create_time` datetime NULL DEFAULT NULL,
    `modify_time` datetime NULL DEFAULT NULL,
    `size` int NULL DEFAULT NULL, -- 文件大小（字节）
    `status` int NULL DEFAULT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 文件描述，可选
    `deleted` bit(1) NULL DEFAULT NULL,
    `user_id` int NULL DEFAULT NULL, -- 上传文件的用户 ID
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_class
-- ----------------------------
DROP TABLE IF EXISTS `t_class`;
CREATE TABLE t_class (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 班级描述，可选
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_class_student
-- ----------------------------
DROP TABLE IF EXISTS `t_class_student`;
CREATE TABLE t_class_student (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_question
-- ----------------------------
DROP TABLE IF EXISTS `t_question`;
CREATE TABLE t_question (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL, -- 用于区分题目类型，例如选择/判断/填空/编程题
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 题目描述，可选
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 题目内容
  `score` int(11) NULL DEFAULT NULL,-- 题目分数
  `difficult` int(11) NULL DEFAULT NULL,-- 题目难度
  `correct` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,-- 题目正确答案
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_question_user_answer
-- ----------------------------
DROP TABLE IF EXISTS `t_question_user_answer`;
CREATE TABLE t_question_user_answer (
  `id` int NOT NULL AUTO_INCREMENT,
  `answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,-- 作答答案
  `score` int(11) NULL DEFAULT NULL,-- 答题分数
  `deleted` bit(1) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,-- 答题者
  `question_id` int NULL DEFAULT NULL,-- 回答的问题
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_proj
-- ----------------------------
DROP TABLE IF EXISTS `t_proj`;
CREATE TABLE t_proj (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 项目描述，可选
  `nums` int(11) NULL DEFAULT NULL,-- 思维链步骤数量
  `deleted` bit(1) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,-- 项目创建者
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_proj_steps
-- ----------------------------
DROP TABLE IF EXISTS `t_proj_step`;
CREATE TABLE t_proj_step (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 思维链步骤描述，可选
  `assess` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 思维链步骤评测，可选
  `finished` bit(1) NULL DEFAULT NULL,-- 该步骤是否完成
  `deleted` bit(1) NULL DEFAULT NULL,
  `proj_id` int NULL DEFAULT NULL,-- 项目id
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_task
-- ----------------------------
DROP TABLE IF EXISTS `t_task`;
CREATE TABLE t_task (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL, -- 用于区分任务类型，例如编程题任务或者项目开发任务
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 发布的任务描述，可选
  `status` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,-- 哪个班级发布的任务
  `question_id` int NULL DEFAULT NULL,-- 题目id
  `proj_id` int NULL DEFAULT NULL,-- 项目id
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ---------------------------------
-- Table structure for t_user_assess
-- ---------------------------------





