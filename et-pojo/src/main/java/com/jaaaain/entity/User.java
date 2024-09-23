package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 存储系统中所有用户的信息(User)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private Integer userId;// 员工ID
    private String username;// 员工姓名
    private String password;// 密码（加密）
    private Integer isAdmin;// 管理员 0: Not admin, 1: Admin
    private Date createTime;// 注册时间
    private Date lastLogin;// 最后登录时间

}

