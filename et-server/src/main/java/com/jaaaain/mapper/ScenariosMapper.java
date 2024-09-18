package com.jaaaain.mapper;

import com.jaaaain.entity.Scenarios;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
     * 查询所有数据
     * @return
     */
    @Select("select * from scenarios")
    List<Scenarios> queryAll();

    /**
     * 查询启用的场景信息
     * @return
     */
    @Select("select * from scenarios where status = 1")
    List<Scenarios> queryEnabled();

    /**
     * 新增数据
     * @param scenarios 实例对象
     * @return 影响行数
     */
    int insert(Scenarios scenarios);

    /**
     * 修改数据
     * @param scenarios 实例对象
     * @return 影响行数
     */
    int update(Scenarios scenarios);

}

