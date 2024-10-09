package com.jaaaain.service;

import com.jaaaain.entity.Ratings;

/**
 * 存储对话的评分结果(Ratings)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface RatingsService {
    /**
     * 通过ID查询单条数据
     * @param ratingId 主键
     * @return 实例对象
     */
    Ratings queryByRatingId(Integer ratingId);

    Ratings queryBySessionId(String sessionId);

    boolean newRating(String sid, String aiResponse);

    boolean deleteById(Integer ratingId);
}
