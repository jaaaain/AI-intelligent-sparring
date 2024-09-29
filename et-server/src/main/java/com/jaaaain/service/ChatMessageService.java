package com.jaaaain.service;


import com.jaaaain.entity.ChatMessage;

/**
 * 存储对话消息的服务接口
 */
public interface ChatMessageService {
    ChatMessage getChatMessage();

    /**
     * 为相应对话插入新的对话消息
     * @param msgContent
     * @return
     */
    ChatMessage newChatMessage(String sessionid,String msgContent,String senderRole);
}
