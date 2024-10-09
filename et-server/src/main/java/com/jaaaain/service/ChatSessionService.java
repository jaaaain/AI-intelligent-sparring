package com.jaaaain.service;

import com.jaaaain.entity.ChatSession;
import com.jaaaain.result.PageBean;
import com.jaaaain.vo.RetSessionVO;

/**
 * 存储用户与AI之间的对话记录服务接口
 * @since 2024-07-23 16:34:25
 */
public interface ChatSessionService {

    PageBean queryForSessions(Integer page, Integer size);

    ChatSession querySessionById(String sessionId);

    /**
     * 新增对话
     * @param sessionId
     * @param userid
     * @param scenarioId
     * @return
     */
    ChatSession newChatSession(String sessionId, Integer userid, Integer scenarioId);

    void deleteSession(String sessionId);
}
