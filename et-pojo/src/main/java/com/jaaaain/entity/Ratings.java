package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

/**
 * 存储对话的评分结果(Ratings)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ratings implements Serializable {
    private Integer ratingId;// 评分ID
    private String sessionId;// 对话ID
    private Integer L;// 维度L评分
    private Integer A;// 维度A评分
    private Integer S;// 维度S评分
    private Integer T;// 维度T评分
    private String L_comment;
    private String A_comment;
    private String S_comment;
    private String T_comment;
    private Integer score;// 平均分
    private String suggestion;// 建议内容
    private LocalDateTime createTime;// 评分时间
}

