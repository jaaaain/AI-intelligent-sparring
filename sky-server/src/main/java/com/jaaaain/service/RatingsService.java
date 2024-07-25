package com.jaaaain.service;

import com.jaaaain.entity.Ratings;
import com.jaaaain.result.PageBean;

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
    Ratings queryById(Integer ratingId);

    /**
     * 分页查询
     * @param ratings 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, Ratings ratings);

    /**
     * 新增数据
     * @param ratings 实例对象
     * @return 实例对象
     */
    Ratings insert(Ratings ratings);

    /**
     * 修改数据
     * @param ratings 实例对象
     * @return 实例对象
     */
    Ratings update(Ratings ratings);

    /**
     * 通过主键删除数据
     * @param ratingId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer ratingId);
}
