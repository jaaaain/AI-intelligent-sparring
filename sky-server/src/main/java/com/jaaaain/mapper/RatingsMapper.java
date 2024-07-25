package com.jaaaain.mapper;

import com.jaaaain.entity.Ratings;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 存储对话的评分结果(Ratings)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface RatingsMapper {
    /**
     * 通过ID查询单条数据
     * @param ratingId 主键
     * @return 实例对象
     */
    Ratings queryById(Integer ratingId);

    /**
     * 查询指定行数据
     * @param ratings 查询条件
     * @return 对象列表
     */
    List<Ratings> queryByLimit(Ratings ratings);

    /**
     * 通过主键删除数据
     * @param ratingId 主键
     * @return 影响行数
     */
    int deleteById(Integer ratingId);
    
    /**
     * 新增数据
     * @param ratings 实例对象
     * @return 影响行数
     */
    int insert(Ratings ratings);

    /**
     * 批量新增数据（foreach）
     * @param entities List<Ratings> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Ratings> entities);

    /**
     * 修改数据
     * @param ratings 实例对象
     * @return 影响行数
     */
    int update(Ratings ratings);

    /**
     * 批量更新数据（foreach）
     * @param entities List<Ratings> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常
     */
    int UpdateBatch(List<Ratings> entities);
    
}

