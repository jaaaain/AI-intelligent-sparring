package com.jaaaain.service;

import com.jaaaain.entity.Ratings;
import com.jaaaain.vo.RatAvgVO;

import java.util.List;

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

    /**
     * 获取单次会话评分
     * @param sessionId
     * @return
     */
    Ratings queryBySessionId(String sessionId);

    /**
     * 创建评分记录
     * @param sid
     * @param aiResponse
     * @return
     */
    boolean newRating(String sid, String aiResponse);

    /**
     * 获取用户所有会话历史评分
     *
     * @param uid
     * @return
     */
    List<Ratings> queryByUserId(String uid);

    /**
     * 获取用户各维度平均分
     *
     * @param uid
     * @return
     */
    RatAvgVO avgByUserId(String uid);
}
