package com.jaaaain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionQueryDTO {
    private Integer page;
    private Integer size;
    private String userId;
    private String scenarioId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
