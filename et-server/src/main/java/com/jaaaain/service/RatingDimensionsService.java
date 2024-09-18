package com.jaaaain.service;

import com.jaaaain.entity.RatingDimensions;
import com.jaaaain.result.PageBean;

import java.util.List;

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
    RatingDimensions queryById(int dimensionId);

    /**
     * 普通分页查询
     * @param page
     * @param size
     * @return
     */
    PageBean queryALLByPage(int page, int size);

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
     * 查询启用的评分维度
     * @return
     */
    List<RatingDimensions> queryEnabled();
}
