package com.jaaaain.mapper;

import com.github.pagehelper.Page;
import com.jaaaain.entity.ChatSession;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 存储用户与AI之间的对话记录(Conversations)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface ConversationsMapper {
    /**
     * 通过ID查询单条数据
     * @param conversationId 主键
     * @return 实例对象
     */
    ChatSession queryById(Integer conversationId);

    /**
     * 查询指定行数据
     * @param chatSession 查询条件
     * @return 对象列表
     */
    List<ChatSession> queryByLimit(ChatSession chatSession);

    /**
     * 通过主键删除数据
     * @param conversationId 主键
     * @return 影响行数
     */
    int deleteById(Integer conversationId);
    
    /**
     * 新增数据
     * @param chatSession 实例对象
     * @return 影响行数
     */
    @Insert("insert into chatsession (session_id, user_id, scenario_id, start_time) value (#{chatSession.sessionId},#{chatSession.userId},#{chatSession.scenarioId},NOW())")
    int insert(ChatSession chatSession);

    /**
     * 批量新增数据（foreach）
     * @param entities List<Conversations> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<ChatSession> entities);

    /**
     * 修改数据
     * @param chatSession 实例对象
     * @return 影响行数
     */
    int update(ChatSession chatSession);

    /**
     * 批量更新数据（foreach）
     * @param entities List<Conversations> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常
     */
    int UpdateBatch(List<ChatSession> entities);

    /**
     * 根据用户ID查询对话记录
     * @param userId
     * @return
     */
    @Select("select * from ChatSession where user_id = #{userId}")
    Page<ChatSession> queryByUserId(String userId);
}

