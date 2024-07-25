package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.Conversations;
import com.jaaaain.mapper.ConversationsMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.ConversationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储用户与AI之间的对话记录(Conversations)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service("conversationsService")
public class ConversationsServiceImpl implements ConversationsService {

    @Autowired
    private ConversationsMapper conversationsMapper;
    /**
     * 通过ID查询单条数据
     * @param conversationId 主键
     * @return 实例对象
     */
    @Override
    public Conversations queryById(Integer conversationId) {
        return conversationsMapper.queryById(conversationId);
    }

    /**
     * 分页查询
     * @param conversations 筛选条件
     * @return 查询结果
     */
    @Override
    public PageBean queryByLimit(Integer page, Integer size, Conversations conversations) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<Conversations> list = conversationsMapper.queryAllByLimit(conversations);  // 得到的数据直接为PageBean类型
        Page<Conversations> p = (Page<Conversations>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 新增数据
     * @param conversations 实例对象
     * @return 实例对象
     */
    @Override
    public Conversations insert(Conversations conversations) {
        conversationsMapper.insert(conversations);
        return conversations;
    }

    /**
     * 修改数据
     * @param conversations 实例对象
     * @return 实例对象
     */
    @Override
    public Conversations update(Conversations conversations) {
        conversationsMapper.update(conversations);
        return queryById(conversations.getConversationId());
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
