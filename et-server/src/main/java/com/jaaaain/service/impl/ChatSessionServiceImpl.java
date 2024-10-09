package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.annotation.CheckPower;
import com.jaaaain.context.BaseContext;
import com.jaaaain.entity.ChatSession;
import com.jaaaain.mapper.ChatMessageMpper;
import com.jaaaain.mapper.ChatSessionMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.ChatSessionService;
import com.jaaaain.vo.RetSessionVO;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 存储用户与AI之间的对话记录(Conversations)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private ChatSessionMapper chatSessionMapper;
    @Autowired
    private ChatMessageMpper chatMessageMpper;

    @Override
    public PageBean queryForSessions(Integer page, Integer size){
        Long currentUserId = BaseContext.getCurrentId();
        Integer currentRole = BaseContext.getCurrentRole();
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<ChatSession> list;
        if (currentRole==1) {
            list = chatSessionMapper.queryAll();  // 得到的数据直接为PageBean类型
        }else{
            list = chatSessionMapper.queryByUserId(currentUserId);
        }
        Page<ChatSession> p = (Page<ChatSession>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    @Override
    @CheckPower("querySessionById")
    public ChatSession querySessionById(String sessionId) {
        return chatSessionMapper.queryById(sessionId);
    }

    @Override
    @Transactional
    @CheckPower("deleteSession")
    public void deleteSession(String sessionId) {
        chatSessionMapper.deleteById(sessionId);
        chatMessageMpper.deleteBySid(sessionId);
    }

    /**
     * 新增对话
     * @param sessionId
     * @param userId
     * @param scenarioId
     * @return
     */
    @Override
    public ChatSession newChatSession(String sessionId, Integer userId, Integer scenarioId) {
        ChatSession chatSession = new ChatSession();
        chatSession.setSessionId(sessionId);
        chatSession.setUserId(userId);
        chatSession.setScenarioId(scenarioId);
        chatSessionMapper.insert(chatSession);
        return chatSession;
    }
}
