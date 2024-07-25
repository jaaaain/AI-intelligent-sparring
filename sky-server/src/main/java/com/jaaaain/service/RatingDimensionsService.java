package com.jaaaain.service;

import com.jaaaain.entity.RatingDimensions;
import com.jaaaain.result.PageBean;

/**
 * 存储评分维度信息(RatingDimensions)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface RatingDimensionsService {
    /**
     * 通过ID查询单条数据
     * @param dimensionId 主键
     * @return 实例对象
     */
    RatingDimensions queryById(Integer dimensionId);

    /**
     * 分页查询
     * @param ratingDimensions 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, RatingDimensions ratingDimensions);

    /**
     * 新增数据
     * @param ratingDimensions 实例对象
     * @return 实例对象
     */
    RatingDimensions insert(RatingDimensions ratingDimensions);

    /**
     * 修改数据
     * @param ratingDimensions 实例对象
     * @return 实例对象
     */
    RatingDimensions update(RatingDimensions ratingDimensions);

    /**
     * 通过主键删除数据
     * @param dimensionId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer dimensionId);
}
