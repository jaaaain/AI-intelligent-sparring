package com.jaaaain.mapper;

import com.jaaaain.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ChatMessageMpper {
    @Insert("insert into chatmessage(session_id, sender_role, content, timestamp) value (#{sessionId},#{senderRole},#{content},NOW())")
    void insert(ChatMessage chatMessage);

    @Select("select (session_id, sender_role, content, timestamp) from chatmessage where session_id=#{sessionId}")
    void selectBySessionId(String sessionId);
}
