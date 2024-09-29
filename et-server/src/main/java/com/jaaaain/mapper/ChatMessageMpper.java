package com.jaaaain.mapper;

import com.jaaaain.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatMessageMpper {
    @Insert("insert into chatmessage(session_id, sender_role, content, timestamp) value (#{sessionId},#{senderRole},#{content},NOW())")
    void insert(ChatMessage chatMessage);
}
