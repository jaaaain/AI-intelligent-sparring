package com.jaaaain.websocket;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.constant.ChatConstant;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.exception.BizException;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.properties.ChatProperties;
import com.jaaaain.service.AiService;
import com.jaaaain.service.ChatMessageService;
import com.jaaaain.service.ChatSessionService;
import com.jaaaain.service.ScenariosService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/api/websocket/conversation/{sid}") // 通过这个地址建立的连接都会通过这个服务端来处理，每一个链接都会创造一个服务端对象
public class WebSocketServer {

    private static Map<String, Session> sessionMap = new HashMap<>();
    private static RedisTemplate redisTemplate;
    private static AiService aiService;

    @Autowired
    public void SetWebSocketService(RedisTemplate redisTemplate, AiService aiService) {
        WebSocketServer.redisTemplate = redisTemplate;
        WebSocketServer.aiService = aiService;
    }

    //定义系列固定名称和参数的方法 onOpen、onMessage、onClose、发送方法
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        sessionMap.put(sid, session);
        // 获取参数
        Map<String, List<String>> map = session.getRequestParameterMap();
        System.out.println(map);
        Integer option = Integer.valueOf(map.get("option").get(0));
        Integer uid = Integer.valueOf(map.get("uid").get(0));
        System.out.println("客户端：" + uid + "  建立连接" + sid + "  option:" + option);
        // 新建对话服务
        String complainMsg = aiService.newOpenAiService(sid,uid,option);
        // 发送抱怨开场白
        RetMsgVO aiGreetingMsgVO = new RetMsgVO("AI", "顾客抱怨: " + complainMsg);
        sendToClient(sid, JSONObject.toJSONString(aiGreetingMsgVO));
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
        String aiResponse;
        if(message.equals("exit")) {
            System.out.println("关闭对话");
            aiResponse = aiService.getFeedBack(sid);
        }
        else {
            aiResponse = aiService.getResponse(sid, message);
        }
        // 发送消息到客户端
        RetMsgVO aiReternMsg = new RetMsgVO("AI", aiResponse);
        sendToClient(sid, JSONObject.toJSONString(aiReternMsg));
    }

    /**
     * 连接关闭调用的方法
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
        sessionMap.remove(sid);
        redisTemplate.delete(sid);
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
}
