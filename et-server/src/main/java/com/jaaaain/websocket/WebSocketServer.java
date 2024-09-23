package com.jaaaain.websocket;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.constant.ChatConstant;
import com.jaaaain.mapper.ConversationsMapper;
import com.jaaaain.properties.ChatProperties;
import com.jaaaain.service.AiService;
import com.jaaaain.service.ChatSessionService;
import com.jaaaain.vo.RetMsgVO;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/api/websocket/conversation/{sid}") // 通过这个地址建立的连接都会通过这个服务端来处理，每一个链接都会创造一个服务端对象
public class WebSocketServer {

    public static final String SESSIONKEY = "session";
    public static final String SERVICEKEY = "aiService";
    public static final String CONVHISKEY = "conversationHistory";

    @Autowired
    private ChatSessionService chatSessionService; // 对话记录数据库存储
    @Autowired
    private RedisTemplate redisTemplate; // 使用 Redis 哈希存储每个 sid 对应的 session、aiService、conversationHistory

    private AiService aiService;
    private ChatProperties chatProperties;

    //定义系列固定名称和参数的方法 onOpen、onMessage、onClose、发送方法
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        Integer option = Integer.valueOf(session.getQueryString().split("option=")[1]);
        Integer uid = Integer.valueOf(session.getQueryString().split("uid=")[1]);
        System.out.println("客户端：" + sid + "建立连接。option:" + option);
        chatSessionService.newChatSession(Integer.valueOf(sid),uid,option);
        // 创建一个ai对话服务
        OpenAiService service = new OpenAiService(chatProperties.getOpenaiKey());


        // 初始化对话历史记录
        List<ChatMessage> conversationHistory = new ArrayList<>();
        conversationHistory.add(new ChatMessage("system", ChatConstant.SYSTEM_INITIAL));
        conversationHistory.add(new ChatMessage("user", ChatConstant.EMPLOYEE_GREETING));
        conversationHistory.add(new ChatMessage("assistant", ChatConstant.TOO_SLOW));

        redisTemplate.opsForHash().put(sid,SESSIONKEY,session);
        redisTemplate.opsForHash().put(sid,SERVICEKEY,service);
        redisTemplate.opsForHash().put(sid,CONVHISKEY,conversationHistory);

        // 发送抱怨开场白
        RetMsgVO aiGreetingMsgVO = new RetMsgVO("AI", "顾客抱怨: " + ChatConstant.TOO_SLOW);
        sendToClient(sid, JSONObject.toJSONString(aiGreetingMsgVO));
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
        OpenAiService service = (OpenAiService) redisTemplate.opsForHash().get(sid,SESSIONKEY);
        List<ChatMessage> conversationHistory = (List<ChatMessage>) redisTemplate.opsForHash().get(sid,CONVHISKEY);

        // 新增对应session的对话历史记录
        ChatMessage userMessage = new ChatMessage("user", message);
        conversationHistory.add(userMessage);

        // AI通过对话历史记录生成回复
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .messages(conversationHistory)
                .model(chatProperties.getModel())
                .maxTokens(150)
                .temperature(chatProperties.getTemperature())
                .build();
        ChatCompletionResult result = service.createChatCompletion(chatCompletionRequest);
        ChatMessage aiResponse = result.getChoices().get(0).getMessage();

        conversationHistory.add(aiResponse);
        redisTemplate.opsForHash().put(sid,CONVHISKEY,conversationHistory);

        // Send AI response to client
        RetMsgVO aiReternMsg = new RetMsgVO("AI", aiResponse.getContent());
        sendToClient(sid, JSONObject.toJSONString(aiReternMsg));
    }

    /**
     * 连接关闭调用的方法
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        OpenAiService service = (OpenAiService) redisTemplate.opsForHash().get(sid,SERVICEKEY);
        System.out.println("对话结束，正在生成评分和反馈...");
        List<ChatMessage> conversationHistory = (List<ChatMessage>) redisTemplate.opsForHash().get(sid,CONVHISKEY);
        conversationHistory.add(new ChatMessage("system",ChatConstant.SYSTEM_FEEDBACK));
        ChatCompletionRequest feedbackCompletionRequest = ChatCompletionRequest
                .builder()
                .messages(conversationHistory)
                .model(chatProperties.getModel())
                .maxTokens(400)
                .temperature(chatProperties.getTemperature())
                .build();
        ChatCompletionResult result = service.createChatCompletion(feedbackCompletionRequest);
        ChatMessage response = result.getChoices().get(0).getMessage();
        conversationHistory.add(response);

        redisTemplate.opsForHash().delete(sid);
    }

    /**
     * 服务器向客户端发送消息
     * @parem message
     */
    public void sendToClient(String sid, String message){
        Session session = (Session) redisTemplate.opsForHash().get(sid,SESSIONKEY);
        if(session != null){
            session.getAsyncRemote().sendText(message);
        }
    }
}
