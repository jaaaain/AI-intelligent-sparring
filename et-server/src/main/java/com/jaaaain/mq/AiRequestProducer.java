package com.jaaaain.mq;

import com.jaaaain.entity.AiRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AiRequestProducer {
    private static final String EXCHANGE_NAME = "ai_exchange";
    private static final String ROUTING_KEY = "ai_request";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendAiRequest(List<ChatMessage> conversationHistory) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, conversationHistory);
        System.out.println("Sent AI request to RabbitMQ: " + conversationHistory);
    }
}