package com.jaaaain.mq;

import com.jaaaain.entity.ChatMessage;
import com.jaaaain.service.AiService;
import com.jaaaain.service.ChatMessageService;
import com.jaaaain.service.ChatSessionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Component
public class AiRequestConsumer {
    @Autowired
    private AiService aiService; // AI 业务逻辑服务
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ChatMessageService chatMessageService;

    @RabbitListener(queues = "ai_request_queue")
    public void processAiRequest(List<ChatMessage> conversationHistory) {
        System.out.println("Received AI request: " + conversationHistory);
        // 调用 AI 服务获取回复
        ChatMessage aiResponse = aiService.onChat(conversationHistory,150);
        System.out.println("aiResponse: " + aiResponse);

        // 这里可以将 AI 回复写入 WebSocket 或数据库，供前端轮询获取

        // 存储操作
        chatMessageService.newChatMessage(sid,message,"员工");
        chatMessageService.newChatMessage(sid,aiResponse.getContent(),"顾客");
        redisTemplate.opsForList().rightPush(sid,aiResponse); // 对话消息记录存入Redis

        return aiResponse.getContent();
    }
}