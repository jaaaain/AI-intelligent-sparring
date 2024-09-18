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
     * 根据用户ID查询对话记录
     * @param userId 用户ID
     * @return 查询结果
     */
    PageBean queryByUserId(Integer page, Integer size, String userId);

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
