package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ChatMessage)实体类
 * @since 2024-09-22 14:59:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements Serializable {
    private Long messageId;// 消息id
    private String sessionId;// 该消息属于ChatSession哪次对话
    private String senderRole;// 消息发送者
    private String content;// 消息内容
    private Date timestamp;// 消息发送时间
}

