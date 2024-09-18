package com.jaaaain.controller;

import com.jaaaain.entity.Scenarios;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.ScenariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 存储不同场景的详细信息(Scenarios)表控制层
 * @since 2024-07-26 15:41:01
 */
@RestController
public class ScenariosController {

    @Autowired
    private ScenariosService scenariosService;

    /**
     * 新增评分维度
     * @param scenarios
     */
    @PostMapping("/admin/scenarios")
    public Result add(@RequestBody Scenarios scenarios) {
        scenariosService.insert(scenarios);
        return Result.success();
    }

    /**
     * 修改评分维度
     * @param scenarios_id,scenarios
     */
    @PutMapping("/admin/scenarios")
    public Result update(@PathVariable int scenarios_id, @RequestBody Scenarios scenarios) {
        scenarios.setScenarioId(scenarios_id);
        scenariosService.update(scenarios);
        return Result.success();
    }

    /**
     * 评分维度列表
     */
    @GetMapping("/admin/scenarios")
    public Result getScenariosList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        PageBean pageBean = scenariosService.queryALLByPage(page,size);
        return Result.success(pageBean);
    }

    /**
     * 获取启用的评分维度信息
     */
    @GetMapping("/scenarios")
    public Result getEnabledScenarios() {
        List<Scenarios> list = scenariosService.queryEnabled();
        return Result.success(list);
    }

}

