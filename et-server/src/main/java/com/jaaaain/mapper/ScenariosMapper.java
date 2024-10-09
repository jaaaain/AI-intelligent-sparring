package com.jaaaain.mapper;

import com.jaaaain.dto.ScenariosCreateDTO;
import com.jaaaain.dto.ScenariosQueryDTO;
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
     */
    @Select("select * from scenarios where scenario_id=#{scenarioId} and status>=0")
    Scenarios queryById(Integer scenarioId);

    @Select("select * from scenarios where scenario_name=#{scenarioName} and status>=0")
    List<Scenarios> queryByName(String scenarioName);

    List<Scenarios> queryList(ScenariosQueryDTO scenariosQueryDTO);

    /**
     * 新增数据
     * @param scenariosCreateDTO 实例对象
     * @return 影响行数
     */
    int insert(ScenariosCreateDTO scenariosCreateDTO);

    /**
     * 修改数据
     * @param scenarios 实例对象
     * @return 影响行数
     */
    int update(Scenarios scenarios);

}

