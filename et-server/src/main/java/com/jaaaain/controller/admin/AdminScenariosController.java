package com.jaaaain.controller.admin;

import com.jaaaain.dto.ScenariosCreateDTO;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.result.Result;
import com.jaaaain.service.ScenariosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 存储不同场景的详细信息(Scenarios)表控制层
 * @since 2024-07-26 15:41:01
 */
@Slf4j
@RestController
@RequestMapping("/admin/scenarios")
public class AdminScenariosController {

    @Autowired
    private ScenariosService scenariosService;

    /**
     * 新增场景
     * @param scenariosCreateDTO
     * @return
     */
    @PostMapping("/")
    public Result add(@Validated @RequestBody ScenariosCreateDTO scenariosCreateDTO) {
        scenariosService.newScenarios(scenariosCreateDTO);
        return Result.success();
    }

    /**
     * 删除场景
     * @param scenarioId
     * @return
     */
    @DeleteMapping("/{scenarioId}")
    public Result delete(@PathVariable Integer scenarioId) {
        log.info("删除场景");
        scenariosService.deleteScenario(scenarioId);
        return Result.success();
    }

    /**
     * 修改场景内容
     * @param scenarioId
     * @param description
     * @return
     */
    @PutMapping("/{scenarioId}")
    public Result updateDescription(@PathVariable int scenarioId, String description) {
        return Result.success(scenariosService.updateDescription(scenarioId,description));
    }

    /**
     * 切换场景显示状态
     * @param scenarioId
     * @return
     */
    @PutMapping("/toggle/{scenarioId}")
    public Result toggleStatus(@PathVariable int scenarioId) {
        return Result.success(scenariosService.toggleStatus(scenarioId));
    }

}

