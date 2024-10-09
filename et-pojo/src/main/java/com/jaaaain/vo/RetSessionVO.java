package com.jaaaain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RetSessionVO {
    private String sessionId; // 对话ID
    private String scenarioName; // 场景名
    private String username; // 用户姓名
//    private LocalDateTime createTime; // 对话创建时间
//    private Duration duration; // 对话持续时间
}
