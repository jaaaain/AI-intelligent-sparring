package com.jaaaain.websocket;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.service.AiService;
import com.jaaaain.vo.RetMsgVO;
import jakarta.websocket.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/api/websocket/conversation/{sid}") // 通过这个地址建立的连接都会通过这个服务端来处理，每一个链接都会创造一个服务端对象
@Slf4j
public class WebSocketServer {

    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>(); // 线程安全的map

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AiService aiService;

    //定义系列固定名称和参数的方法 onOpen、onMessage、onClose、发送方法

    /**
     * 连接建立成功调用的方法
     * @param session 与某个客户端的连接会话，需要通过它来给客户端发送消息
     * @param sid 每次页面建立连接时传入到服务端的id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        sessionMap.put(sid, session);
        // 获取参数
        Map<String, List<String>> map = session.getRequestParameterMap();
        log.info("收到session：{}",map);

        Integer option = Integer.valueOf(map.get("option").get(0));
        Integer uid = Integer.valueOf(map.get("uid").get(0));
        log.info("客户端：{} 建立连接：{} option：{}",uid,sid,option);

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
     * 客户端意外断开时清理session
     * @param session
     * @param sid
     * @param error
     */
    @OnError
    public void onError(Session session, @PathParam("sid") String sid, Throwable error) {
        System.out.println("WebSocket 连接异常: " + sid);
        sessionMap.remove(sid);
        redisTemplate.delete(sid);
        error.printStackTrace();
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
