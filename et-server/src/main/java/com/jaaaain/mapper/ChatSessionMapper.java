package com.jaaaain.mapper;

import com.github.pagehelper.Page;
import com.jaaaain.entity.ChatSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 存储用户与AI之间的对话记录(Conversations)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface ChatSessionMapper {
    /**
     * 通过ID查询单条数据
     * @param sessionId 主键
     * @return 实例对象
     */
    ChatSession queryById(String sessionId);

    /**
     * 通过主键删除数据
     * @param sessionId 主键
     * @return 影响行数
     */
    @Delete("delete from chatsession where session_id=#{sessionId}")
    int deleteById(String sessionId);
    
    /**
     * 新增数据
     * @param chatSession 实例对象
     * @return 影响行数
     */
    @Insert("insert into chatsession (session_id, user_id, scenario_id, start_time) value (#{sessionId},#{userId},#{scenarioId},NOW())")
    int insert(ChatSession chatSession);

    /**
     * 根据用户ID查询对话记录
     * @param userId
     * @return
     */
    @Select("select * from ChatSession where user_id = #{userId}")
    Page<ChatSession> queryByUserId(Long userId);

    /**
     * 查询对话记录
     * @return
     */
    @Select("select * from ChatSession")
    Page<ChatSession> queryAll();

}

