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
  `grade_level`  int NULL DEFAULT NULL,-- 大学年级
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,-- 专业
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
    `status` int NULL DEFAULT NULL,
    `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, -- 文件描述，可选
    `deleted` bit(1) NULL DEFAULT NULL,
    `class_id` int NULL DEFAULT NULL, -- 文件所在班级的ID
    `user_id` int NULL DEFAULT NULL, -- 上传文件的用户 ID
    `is_ai_gen` bit(1) NULL DEFAULT NULL, -- 是否是AI生成的图片
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
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL, 
  `format` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `example` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `difficult` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `tips` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `modify_time` datetime NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `class_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_proj_step
-- ----------------------------
DROP TABLE IF EXISTS `t_programming_assess`;
CREATE TABLE t_programming_assess (
  `id` int NOT NULL AUTO_INCREMENT,
  `code_type` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `feedback` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `modified_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `error_analysis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `optimization_suggestions` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `question_id` int(11) NULL DEFAULT NULL,
  `user_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB  CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for t_user_event_log
-- ----------------------------
DROP TABLE IF EXISTS `t_user_event_log`;
CREATE TABLE `t_user_event_log`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `deleted` bit(1) NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = COMPACT;





