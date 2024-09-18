package com.jaaaain.mapper;

import com.jaaaain.entity.RatingDimensions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 存储评分维度信息(RatingDimensions)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface RatingDimensionsMapper {
    /**
     * 通过ID查询单条数据
     * @param dimensionId 主键
     * @return 实例对象
     */
    RatingDimensions queryById(Integer dimensionId);

    /**
     * 查询所有数据
     * @return
     */
    @Select("select * from rating_dimensions")
    List<RatingDimensions> queryALL();

    /**
     * 查询启用的评分维度信息
     * @return
     */
    @Select("select * from rating_dimensions where status = 1")
    List<RatingDimensions> queryEnabled();

    /**
     * 新增数据
     * @param ratingDimensions 实例对象
     * @return 影响行数
     */
    int insert(RatingDimensions ratingDimensions);

    /**
     * 修改数据
     * @param ratingDimensions 实例对象
     * @return 影响行数
     */
    int update(RatingDimensions ratingDimensions);

}

