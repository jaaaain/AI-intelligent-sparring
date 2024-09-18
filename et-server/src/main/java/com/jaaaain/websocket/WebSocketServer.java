package com.jaaaain.websocket;

import com.alibaba.fastjson.JSONObject;
import com.jaaaain.vo.RetMsgVO;
import org.springframework.stereotype.Component;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket服务
 */
@Component
@ServerEndpoint("/api/websocket/conversation/{sid}") // 通过这个地址建立的连接都会通过这个服务端来处理，每一个链接都会创造一个服务端对象
public class WebSocketServer {
    //存放会话对象
    private static Map<String, Session> sessionMap = new HashMap(); // 存储 sid 和 session

    //定义系列固定名称和参数的方法 onOpen、onMessage、onClose、发送方法
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        Integer option = Integer.valueOf(session.getQueryString().split("option=")[1]);
        System.out.println("客户端：" + sid + "建立连接。option:" + option);
        sessionMap.put(sid, session);
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        System.out.println("收到来自客户端：" + sid + "的信息:" + message);
        String retmsg = "收到来自客户端：" + sid + "的信息:" + message + "接下来我会让AI回复你！！";
        RetMsgVO retmsgVO = new RetMsgVO("AI",retmsg);
        sendToClient(sid, JSONObject.toJSONString(retmsgVO));
    }

    /**
     * 连接关闭调用的方法
     * @param sid
     */
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        System.out.println("连接断开:" + sid);
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

}
