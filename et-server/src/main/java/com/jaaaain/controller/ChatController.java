package com.jaaaain.controller;

import com.jaaaain.context.BaseContext;
import com.jaaaain.entity.ChatMessage;
import com.jaaaain.entity.ChatSession;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.ChatMessageService;
import com.jaaaain.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 聊天控制层，包括根据用户ID查询所有
 */
@RestController()
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatSessionService chatSessionService;
    @Autowired
    private ChatMessageService chatMessageService;

    @GetMapping("/session")
    public Result GetSession(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size) {
        PageBean pageBean = chatSessionService.queryForSessions(page,size);
        return Result.success(pageBean);
    }

    @DeleteMapping("/session/{sid}")
    public Result DeleteSession(@PathVariable("sid") String sessionId) {
        chatSessionService.deleteSession(sessionId);
        return Result.success();
    }

    // 获取某个 session
    @GetMapping("/session/{sid}")
    public Result SessionDetail(@PathVariable("sid") String sessionId) {
        Long currentUserId = BaseContext.getCurrentId();
        Integer currentRole = BaseContext.getCurrentRole();

        if (currentRole!=1) {
            // 非管理员不能获取他人对话记录
            ChatSession chatSession = chatSessionService.querySessionById(sessionId);
            if (!chatSession.getUserId().equals(currentUserId)) {
                return Result.error("非管理员不能获取他人对话记录");
            }
        }
        List<ChatMessage> chatMessage = chatMessageService.queryBySid(sessionId);
        return Result.success(chatMessage);
    }
}
