package com.jaaaain.service.impl;

import com.jaaaain.constant.ChatConstant;
import com.jaaaain.entity.AiRequest;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.mq.AiRequestProducer;
import com.jaaaain.properties.ChatProperties;
import com.jaaaain.service.*;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AiServiceImpl implements AiService {

    @Autowired
    private ChatProperties chatProperties;
    @Autowired
    private ScenariosService scenariosService;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatSessionService chatSessionService;
    @Autowired
    private RatingsService ratingsService;
    @Autowired
    private RedisTemplate redisTemplate; // 使用 Redis 哈希存储每个 sid 对应的 conversationHistory
    @Autowired
    private AiRequestProducer aiRequestProducer;
    // TODO 注入生产者，再重建一个消费者，等有getResponse请求的时候 取出 sid 并从 Redis 获取历史消息，
    //  调用 onChat 处理请求，存储 AI 回复，并使用 WebSocket 推送 AI 响应给前端

    private static OpenAiService service;

    @Override
    public String newOpenAiService(String sid, Integer option, Integer uid) {
        // 实例化ai对话服务
        service = new OpenAiService(chatProperties.getOpenaiKey());
        // 从数据库获取对应场景的抱怨开场白
        Scenarios scenarios = scenariosService.queryById(option);
        String complainMsg = scenarios.getDescription();

        // 初始化对话历史记录
        List<ChatMessage> conversationHistory = new ArrayList<>();
        conversationHistory.add(new ChatMessage("system", ChatConstant.SYSTEM_INITIAL));
        conversationHistory.add(new ChatMessage("assistant", complainMsg));

        // 存储操作
        chatSessionService.newChatSession(sid,uid,option); // 数据库插入新对话
        chatMessageService.newChatMessage(sid,complainMsg,"顾客");
        redisTemplate.opsForList().rightPushAll(sid,conversationHistory);// 对话消息记录存入Redis

        System.out.println("conversationHistory: " + conversationHistory);

        return complainMsg;
    }

    @Override
    public String getResponse(String sid, String message) {
        setProperty();
        // 新增对应session的对话记录
        ChatMessage userMessage = new ChatMessage("user", message);
        redisTemplate.opsForList().rightPush(sid,userMessage);
        // 获取所有历史消息
        List<ChatMessage> conversationHistory = (List<ChatMessage>) redisTemplate.opsForList().range(sid,0,-1);
        System.out.println("conversationHistory: " + conversationHistory);


        // 获取AI回复
        //TODO 放入消息队列，立刻返回 “响应中...”
        // 发送消息到 RabbitMQ
        aiRequestProducer.sendAiRequest(conversationHistory);

        // AI 响应将在消费者（Consumer）中异步处理，这里可以返回一个 "处理中" 提示
        return "AI 处理中，请稍后...";
//        ChatMessage aiResponse = onChat(conversationHistory,150);
//        System.out.println("aiResponse: " + aiResponse);
//
//        // 存储操作
//        chatMessageService.newChatMessage(sid,message,"员工");
//        chatMessageService.newChatMessage(sid,aiResponse.getContent(),"顾客");
//        redisTemplate.opsForList().rightPush(sid,aiResponse); // 对话消息记录存入Redis
//
//        return aiResponse.getContent();
    }

    @Override
    public String getFeedBack(String sid) {
        System.out.println("正在生成评分和反馈...");
        // 新增对应session的对话历史记录
        ChatMessage systemFeedback = new ChatMessage("system",ChatConstant.SYSTEM_FEEDBACK);
        redisTemplate.opsForList().rightPush(sid,systemFeedback);
        // 获取所有历史消息
        List<ChatMessage> conversationHistory = (List<ChatMessage>) redisTemplate.opsForList().range(sid,0,-1);
        System.out.println("conversationHistory: " + conversationHistory);
        // 获取评分
        boolean ratingSuccess=false;
        ChatMessage aiResponse = new ChatMessage();
        Integer count = 0;
        while (!ratingSuccess) {
            count++;
            // 获取AI回复
            setProperty();
            aiResponse = onChat(conversationHistory,255);
            System.out.println(count +" aiResponse: " + aiResponse);
            // 存储操作
            ratingSuccess = ratingsService.newRating(sid,aiResponse.getContent());
        }
        redisTemplate.opsForList().rightPush(sid,aiResponse);
        return aiResponse.getContent();
    }

    public void setProperty() {
        // 设置代理
        System.setProperty("http.proxyHost", chatProperties.getProxyHost());
        System.setProperty("http.proxyPort", chatProperties.getProxyPort());
        System.setProperty("https.proxyHost", chatProperties.getProxyHost());
        System.setProperty("https.proxyPort", chatProperties.getProxyPort());
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
    }

    public ChatMessage onChat(List<ChatMessage> conversationHistory,Integer maxTokens){// 获取AI回应

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .messages(conversationHistory)
                .model(chatProperties.getModel())
                .maxTokens(maxTokens)
                .temperature(chatProperties.getTemperature())
                .build();
        ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
        ChatMessage aiResponse = result.getChoices().get(0).getMessage();
        return aiResponse;
    }
}
