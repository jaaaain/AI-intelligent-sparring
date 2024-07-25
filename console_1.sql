drop database if exists  `emp-train`;
create database `emp-train`;
use `emp-train`;

drop table if exists `user`;
-- 创建用户表
CREATE TABLE user (
    user_id VARCHAR(6) PRIMARY KEY COMMENT '员工ID',
    username VARCHAR(32) NOT NULL COMMENT '员工姓名',
    password VARCHAR(255) NOT NULL COMMENT '密码（加密）',
    is_admin INT NOT NULL DEFAULT '0' COMMENT '管理员 0: Not admin, 1: Admin',
    create_time DATETIME NOT NULL COMMENT '注册时间',
    last_login DATETIME COMMENT '最后登录时间'
) COMMENT='存储系统中所有用户的信息';

drop table if exists `scenarios`;
-- 创建场景表
CREATE TABLE scenarios (
    scenario_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '场景ID',
    scenario_name VARCHAR(50) UNIQUE NOT NULL COMMENT '场景名称',
    description VARCHAR(255) COMMENT '场景描述',
    status INT NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用'
) COMMENT='存储不同场景的详细信息';

drop table if exists `conversations`;
-- 创建对话记录表
CREATE TABLE conversations (
    conversation_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '对话ID',
    user_id VARCHAR(6) COMMENT '员工ID',
    scenario_id INT NOT NULL COMMENT '场景ID',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    content MEDIUMTEXT COMMENT '对话内容'
) COMMENT='存储用户与AI之间的对话记录';

drop table if exists `rating_dimensions`;
-- 创建评分维度表
CREATE TABLE rating_dimensions (
    dimension_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评分维度ID',
    dimension_name VARCHAR(50) UNIQUE NOT NULL COMMENT '维度名称',
    description VARCHAR(255) COMMENT '维度描述',
    status INT NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:启用'
) COMMENT='存储评分维度信息';

drop table if exists `ratings`;
-- 创建评分表
CREATE TABLE ratings (
    rating_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '评分ID',
    conversation_id INT NOT NULL COMMENT '对话ID',
    dimension_id INT NOT NULL COMMENT '评分维度ID',
    score INT NOT NULL COMMENT '评分值',
    content TEXT COMMENT '建议内容',
    create_time DATETIME NOT NULL COMMENT '评分时间'
) COMMENT='存储对话的评分结果';