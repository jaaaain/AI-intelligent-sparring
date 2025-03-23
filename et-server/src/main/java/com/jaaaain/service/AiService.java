package com.jaaaain.service;

import com.jaaaain.entity.ChatMessage;

import java.util.List;

public interface AiService {

    String newOpenAiService(String sid, Integer option, Integer uid);

    String getResponse(String sid, String message);

    String getFeedBack(String sid);

    ChatMessage onChat(List<ChatMessage> conversationHistory, Integer maxTokens);
}
