package com.jaaaain.service;

import com.jaaaain.entity.RatingDimensions;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.result.PageBean;

import java.util.List;

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
    Scenarios queryById(int scenarioId);

    /**
     * 普通分页查询
     * @param page,size
     * @return
     */
    PageBean queryALLByPage(int page, int size);

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
     * 查询启用的评分维度
     * @return
     */
    List<Scenarios> queryEnabled();
}
