package com.jaaaain.mapper;

import com.jaaaain.entity.RatingDimensions;
import org.apache.ibatis.annotations.Mapper;

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
     * 查询指定行数据
     * @param ratingDimensions 查询条件
     * @return 对象列表
     */
    List<RatingDimensions> queryByLimit(RatingDimensions ratingDimensions);

    /**
     * 通过主键删除数据
     * @param dimensionId 主键
     * @return 影响行数
     */
    int deleteById(Integer dimensionId);
    
    /**
     * 新增数据
     * @param ratingDimensions 实例对象
     * @return 影响行数
     */
    int insert(RatingDimensions ratingDimensions);

    /**
     * 批量新增数据（foreach）
     * @param entities List<RatingDimensions> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<RatingDimensions> entities);

    /**
     * 修改数据
     * @param ratingDimensions 实例对象
     * @return 影响行数
     */
    int update(RatingDimensions ratingDimensions);

    /**
     * 批量更新数据（foreach）
     * @param entities List<RatingDimensions> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常
     */
    int UpdateBatch(List<RatingDimensions> entities);
    
}

