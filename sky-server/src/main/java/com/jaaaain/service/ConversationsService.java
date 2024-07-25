package com.jaaaain.service;

import com.jaaaain.entity.Conversations;
import com.jaaaain.result.PageBean;

/**
 * 存储用户与AI之间的对话记录(Conversations)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface ConversationsService {
    /**
     * 通过ID查询单条数据
     * @param conversationId 主键
     * @return 实例对象
     */
    Conversations queryById(Integer conversationId);

    /**
     * 分页查询
     * @param conversations 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, Conversations conversations);

    /**
     * 新增数据
     * @param conversations 实例对象
     * @return 实例对象
     */
    Conversations insert(Conversations conversations);

    /**
     * 修改数据
     * @param conversations 实例对象
     * @return 实例对象
     */
    Conversations update(Conversations conversations);

    /**
     * 通过主键删除数据
     * @param conversationId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer conversationId);
}
