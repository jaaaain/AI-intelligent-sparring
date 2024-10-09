package com.jaaaain.exception;

import lombok.Getter;

public enum BizExceptionEnum {
    // 1xxxx: 场景错误
    SCENARIO_EXIST(10001,"场景已存在"),
    SCENARIO_NOT_EXIST(10002,"场景不存在"),
    DESCRIPTION_NOT_CHANGE(10003,"场景描述未改变"),
    // 2xxxx: 用户级别错误
    //  - 20xxx: 用户错误
    USER_PASSWORD_ERROR(20001,"用户密码错误"),
    USER_NOT_EXIST(20002,"用户不存在"),
    USER_EXIST(20003,"用户已存在"),
    //  - 21xxx: JWT令牌错误
    TOKEN_EXPIRED(21001,"令牌过期"),
    TOKEN_INVALID(21002,"令牌签名无效"),
    PARSING_FAILED(21003,"令牌解析失败"),
    NOT_LOGIN(21004,"用户未登录"),
    //  - 22xxx: 权限错误
    NOT_ADMIN(22001,"没有管理员权限"),
    NOT_THE_PERSON(22002,"非本人"),

    // 3xxxx: 数据库级别报错
    ALREADY_EXISTS(30001,"该数据已存在"),
    UNKNOWN_ERROR(30002,"数据库发生未知错误"),

    // 4xxxx: socket错误
    CONNECT_LOSE(40001,"连接断开")

    ;


    @Getter
    private int code;
    @Getter
    private String msg;

    BizExceptionEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
