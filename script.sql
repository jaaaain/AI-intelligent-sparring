create table chatmessage
(
    message_id  bigint auto_increment comment '消息id'
        primary key,
    session_id  bigint                              not null comment '该消息属于ChatSession哪次对话',
    sender_role varchar(50)                         null comment '消息发送者',
    content     text                                null comment '消息内容',
    timestamp   timestamp default CURRENT_TIMESTAMP null comment '消息发送时间'
);

create table chatsession
(
    session_id  bigint   not null comment '对话ID'
        primary key,
    user_id     bigint   null comment '员工ID',
    scenario_id int      not null comment '场景ID',
    start_time  datetime not null comment '开始时间',
    end_time    datetime null comment '结束时间'
)
    comment '存储用户与AI之间的对话记录';

create table rating_dimensions
(
    dimension_id   int auto_increment comment '评分维度ID'
        primary key,
    dimension_name varchar(50)   not null comment '维度名称',
    description    varchar(255)  null comment '维度描述',
    status         int default 1 not null comment '状态 0:禁用，1:启用',
    constraint dimension_name
        unique (dimension_name)
)
    comment '存储评分维度信息';

create table ratings
(
    rating_id       int auto_increment comment '评分ID'
        primary key,
    conversation_id int      not null comment '对话ID',
    dimension_id    int      not null comment '评分维度ID',
    score           int      not null comment '评分值',
    content         text     null comment '建议内容',
    create_time     datetime not null comment '评分时间'
)
    comment '存储对话的评分结果';

create table scenarios
(
    scenario_id   int auto_increment comment '场景ID'
        primary key,
    scenario_name varchar(50)   not null comment '场景名称',
    description   varchar(255)  null comment '场景描述',
    status        int default 1 not null comment '状态 0:禁用，1:启用',
    constraint scenario_name
        unique (scenario_name)
)
    comment '存储不同场景的详细信息';

create table user
(
    user_id     bigint        not null comment '员工ID'
        primary key,
    username    varchar(32)   not null comment '员工姓名',
    password    varchar(255)  not null comment '密码（加密）',
    is_admin    int default 0 not null comment '管理员 0: Not admin, 1: Admin',
    create_time datetime      not null comment '注册时间',
    last_login  datetime      null comment '最后登录时间'
)
    comment '存储系统中所有用户的信息';


