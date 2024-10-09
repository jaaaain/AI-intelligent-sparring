package com.jaaaain.service.impl;


import com.jaaaain.entity.ChatMessage;
import com.jaaaain.mapper.ChatMessageMpper;
import com.jaaaain.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMpper chatMessageMpper;

    @Override
    public List<ChatMessage> queryBySid(String sessionId) {
        return chatMessageMpper.queryBySid(sessionId);
    }

    @Override
    public void deleteBySid(String sessionId) {
        chatMessageMpper.deleteBySid(sessionId);
    }

    @Override
    public ChatMessage newChatMessage(String sessionid, String msgContent,String senderRole){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(msgContent);
        chatMessage.setSessionId(sessionid);
        chatMessage.setSenderRole(senderRole);
        chatMessageMpper.insert(chatMessage);
        return chatMessage;


    }
}
