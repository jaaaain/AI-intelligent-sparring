package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * 存储用户与AI之间的对话记录(Conversations)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Conversations implements Serializable {
    private Integer conversationId;// 对话ID
    private String userId;// 员工ID
    private Integer scenarioId;// 场景ID
    private Date startTime;// 开始时间
    private Date endTime;// 结束时间
    private String content;// 对话内容
}

