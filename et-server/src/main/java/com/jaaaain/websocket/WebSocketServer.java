package com.jaaaain.websocket;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.constant.ChatConstant;
import com.jaaaain.properties.ChatProperties;
import com.jaaaain.service.AiService;
import com.jaaaain.vo.RetMsgVO;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Autowired;
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
    //存放会话对象
    private static Map<String, Session> sessionMap = new HashMap(); // 存储 sid 和 session
    private static Map<String,OpenAiService> aiServiceMap = new HashMap();
    private AiService aiService;
    private ChatProperties chatProperties;

    @Autowired
    public WebSocketServer(AiService aiService, ChatProperties chatProperties) {
        this.aiService = aiService;
        this.chatProperties = chatProperties;
    }

    //定义系列固定名称和参数的方法 onOpen、onMessage、onClose、发送方法
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        Integer option = Integer.valueOf(session.getQueryString().split("option=")[1]);
        System.out.println("客户端：" + sid + "建立连接。option:" + option);
        sessionMap.put(sid, session);
        // 创建一个ai对话服务
        OpenAiService service = new OpenAiService(chatProperties.getOpenaiKey());
        aiServiceMap.put(sid,service);

        // 初始化对话历史记录
        List<ChatMessage> conversationHistory = new ArrayList<>();
        conversationHistory.add(new ChatMessage("system", ChatConstant.SYSTEM_INITIAL));
        conversationHistory.add(new ChatMessage("user", ChatConstant.EMPLOYEE_GREETING));
        conversationHistory.add(new ChatMessage("assistant", ChatConstant.TOO_SLOW));

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
        OpenAiService service = aiServiceMap.get(sid);

        // 新增对应session的对话历史记录
        ChatMessage userMessage = new ChatMessage("user", message);
        List<ChatMessage> conversationHistory = getConversationHistory(sid); // 通过sid获取对应session的对话历史记录
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
        OpenAiService service = aiServiceMap.get(sid);
        System.out.println("对话结束，正在生成评分和反馈...");
        List<ChatMessage> conversationHistory = getConversationHistory(sid); // 通过sid获取对应session的对话历史记录
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
        aiServiceMap.remove(sid);
        sessionMap.remove(sid);
    }

    /**
     * 服务器向客户端发送消息
     * @parem message
     */
    public void sendToClient(String sid, String message){
        Session session = sessionMap.get(sid);
        if(session != null){
            session.getAsyncRemote().sendText(message);
        }
    }

    private List<ChatMessage> getConversationHistory(String sid) {

    }
}
