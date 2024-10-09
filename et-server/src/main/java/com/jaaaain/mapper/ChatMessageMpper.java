package com.jaaaain.mapper;

import com.jaaaain.entity.ChatMessage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatMessageMpper {
    @Insert("insert into chatmessage(session_id, sender_role, content, timestamp) value (#{sessionId},#{senderRole},#{content},NOW())")
    void insert(ChatMessage chatMessage);

    @Select("select * from chatmessage where session_id=#{sessionId}")
    List<ChatMessage> queryBySid(String sessionId);

    @Delete("delete from chatmessage where session_id=#{sessionId}")
    void deleteBySid(String sessionId);
}
