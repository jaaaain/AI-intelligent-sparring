package com.jaaaain.service;

import com.jaaaain.dto.ScenariosCreateDTO;
import com.jaaaain.dto.ScenariosQueryDTO;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.result.PageBean;

/**
 * 存储不同场景的详细信息(Scenarios)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface ScenariosService {
    Scenarios queryById(Integer scenarioId);
    /**
     * 查询
     * @param searchDTO
     * @return
     */
    PageBean queryForScenarios(ScenariosQueryDTO searchDTO);

    /**
     * 新增数据
     * @param scenariosCreateDTO
     */
    void newScenarios(ScenariosCreateDTO scenariosCreateDTO);

    /**
     * 修改数据
     *
     * @param scenarioId
     * @return 实例对象
     */
    Scenarios toggleStatus(Integer scenarioId);

    Scenarios updateDescription(Integer scenarioId, String description);

    void deleteScenario(Integer scenarioId);

}
