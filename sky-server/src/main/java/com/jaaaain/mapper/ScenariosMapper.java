package com.jaaaain.mapper;

import com.jaaaain.entity.Scenarios;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 存储不同场景的详细信息(Scenarios)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
 @Mapper
public interface ScenariosMapper {
    /**
     * 通过ID查询单条数据
     * @param scenarioId 主键
     * @return 实例对象
     */
    Scenarios queryById(Integer scenarioId);

    /**
     * 查询指定行数据
     * @param scenarios 查询条件
     * @return 对象列表
     */
    List<Scenarios> queryByLimit(Scenarios scenarios);

    /**
     * 通过主键删除数据
     * @param scenarioId 主键
     * @return 影响行数
     */
    int deleteById(Integer scenarioId);
    
    /**
     * 新增数据
     * @param scenarios 实例对象
     * @return 影响行数
     */
    int insert(Scenarios scenarios);

    /**
     * 批量新增数据（foreach）
     * @param entities List<Scenarios> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(List<Scenarios> entities);

    /**
     * 修改数据
     * @param scenarios 实例对象
     * @return 影响行数
     */
    int update(Scenarios scenarios);

    /**
     * 批量更新数据（foreach）
     * @param entities List<Scenarios> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常
     */
    int UpdateBatch(List<Scenarios> entities);
    
}

