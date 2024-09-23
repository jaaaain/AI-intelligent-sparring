package com.jaaaain.controller;

import com.jaaaain.entity.ChatSession;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 存储用户与AI之间的对话记录(Conversations)表控制层
 * @since 2024-7-30 16:53:16
 */
@RestController
@RequestMapping
public class ConversationsController {

    @Autowired
    private ChatSessionService chatSessionService;

    /**
     * 根据用户ID查询历史对话列表
     * @param userId
     * @return 查询结果
     */
    @GetMapping("/conversations/user/{user_id}")
    public Result list(@PathVariable("user_id") String userId,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer size) {
        PageBean pageBean = chatSessionService.queryByUserId(page,size,userId);
        return Result.success(pageBean);
    }

    /**
     * 通过主键查询单条数据
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("/conversations/{conversations_id}")
    public Result queryById(@PathVariable("conversations_id") Integer id) {
        ChatSession chatSession = chatSessionService.queryById(id);
        return Result.success(chatSession);
    }

//    /**
//     * 新增数据
//     * @param chatSession 实体
//     * @return 新增结果
//     */
//    @PostMapping("/conversations")
//    public Result add(ChatSession chatSession) {
//        chatSessionService.newChatSession(chatSession);
//        return Result.success();
//    }

    /**
     * 编辑数据
     * @param chatSession 实体
     * @return 编辑结果
     */
    @PutMapping("/conversations/{conversations_id}")
    public Result edit(ChatSession chatSession) {
        chatSessionService.update(chatSession);
        return Result.success();
    }
}

