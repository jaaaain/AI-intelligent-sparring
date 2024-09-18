package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Integer conversationId;// 对话ID
    private Integer dimensionId;// 评分维度ID
    private Integer score;// 评分值
    private String content;// 建议内容
    private Date createTime;// 评分时间
}

