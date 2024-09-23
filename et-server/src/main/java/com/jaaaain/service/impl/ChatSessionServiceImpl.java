package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.ChatSession;
import com.jaaaain.mapper.ConversationsMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.ChatSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储用户与AI之间的对话记录(Conversations)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service("conversationsService")
public class ChatSessionServiceImpl implements ChatSessionService {

    @Autowired
    private ConversationsMapper conversationsMapper;

    /**
     * 通过ID查询单条数据
     * @param conversationId 主键
     * @return 实例对象
     */
    @Override
    public ChatSession queryById(Integer conversationId) {
        return conversationsMapper.queryById(conversationId);
    }

    /**
     * 根据用户ID查询对话记录
     * @param userId 用户ID
     * @return 查询结果
     */
    @Override
    public PageBean queryByUserId(Integer page, Integer size, String userId) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<ChatSession> list = conversationsMapper.queryByUserId(userId);  // 得到的数据直接为PageBean类型
        Page<ChatSession> p = (Page<ChatSession>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 新增对话
      * @param sessionid
     * @param userid
     * @param scenarioid
     * @return
     */
    public ChatSession newChatSession(Integer sessionid, Integer userid, Integer scenarioid) {
        ChatSession chatSession = new ChatSession();
        chatSession.setSessionId(sessionid);
        chatSession.setUserId(userid);
        chatSession.setScenarioId(scenarioid);
        conversationsMapper.insert(chatSession);
        return chatSession;
    }

    /**
     * 修改数据
     * @param chatSession 实例对象
     * @return 实例对象
     */
    @Override
    public ChatSession update(ChatSession chatSession) {
        conversationsMapper.update(chatSession);
        return queryById(chatSession.getSessionId());
    }

    /**
     * 通过主键删除数据
     * @param conversationId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer conversationId) {
        return conversationsMapper.deleteById(conversationId) > 0;
    }
}
