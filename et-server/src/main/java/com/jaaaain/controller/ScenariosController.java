package com.jaaaain.controller;

import com.jaaaain.dto.ScenariosQueryDTO;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.ScenariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 存储不同场景的详细信息(Scenarios)表控制层
 * @since 2024-07-26 15:41:01
 */
@RestController
@RequestMapping("/scenarios")
public class ScenariosController {

    @Autowired
    private ScenariosService scenariosService;

    /**
     * 场景列表
     */
    @GetMapping("/list")
    public Result getScenariosList(@Validated ScenariosQueryDTO scenariosQueryDTO) {
        PageBean pageBean = scenariosService.queryForScenarios(scenariosQueryDTO);
        return Result.success(pageBean);
    }
}

