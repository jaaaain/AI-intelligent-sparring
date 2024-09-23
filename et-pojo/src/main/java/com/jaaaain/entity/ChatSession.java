package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * 存储用户与AI之间的对话记录(Conversations)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatSession implements Serializable {
    private Integer sessionId;// 对话ID
    private Integer userId;// 员工ID
    private Integer scenarioId;// 场景ID
    private LocalDateTime startTime;// 开始时间
    private LocalDateTime endTime;// 结束时间
}

