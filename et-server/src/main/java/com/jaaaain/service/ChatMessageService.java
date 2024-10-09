package com.jaaaain.service;


import com.jaaaain.entity.ChatMessage;

import java.util.List;

/**
 * 存储对话消息的服务接口
 */
public interface ChatMessageService {
    List<ChatMessage> queryBySid(String sessionId);
    void deleteBySid(String sessionId);

    /**
     * 为相应对话插入新的对话消息
     * @param msgContent
     * @return
     */
    ChatMessage newChatMessage(String sessionid,String msgContent,String senderRole);
}
