package com.jaaaain.service;

import com.jaaaain.entity.Scenarios;
import com.jaaaain.result.PageBean;

/**
 * 存储不同场景的详细信息(Scenarios)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface ScenariosService {
    /**
     * 通过ID查询单条数据
     * @param scenarioId 主键
     * @return 实例对象
     */
    Scenarios queryById(Integer scenarioId);

    /**
     * 分页查询
     * @param scenarios 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, Scenarios scenarios);

    /**
     * 新增数据
     * @param scenarios 实例对象
     * @return 实例对象
     */
    Scenarios insert(Scenarios scenarios);

    /**
     * 修改数据
     * @param scenarios 实例对象
     * @return 实例对象
     */
    Scenarios update(Scenarios scenarios);

    /**
     * 通过主键删除数据
     * @param scenarioId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer scenarioId);
}
