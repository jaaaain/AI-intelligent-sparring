package com.jaaaain.controller;

import com.jaaaain.entity.RatingDimensions;
import com.jaaaain.result.PageBean;
import com.jaaaain.result.Result;
import com.jaaaain.service.RatingDimensionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 存储评分维度信息(RatingDimensions)表控制层
 * @since 2024-07-26 14:50:29
 */
@RestController
public class RatingDimensionsController {

    @Autowired
    private RatingDimensionsService ratingDimensionsService;

    /**
     * 新增评分维度
     * @param ratingDimensions
     */
    @PostMapping("/admin/dimensions")
    public Result add(@RequestBody RatingDimensions ratingDimensions) {
        ratingDimensionsService.insert(ratingDimensions);
        return Result.success();
    }

    /**
     * 修改评分维度
     * @param dimension_id,description
     */
    @PutMapping("/admin/dimensions")
    public Result update(@PathVariable int dimension_id,@RequestBody RatingDimensions ratingDimensions) {
        ratingDimensions.setDimensionId(dimension_id);
        ratingDimensionsService.update(ratingDimensions);
        return Result.success();
    }

    /**
     * 评分维度列表
     */
    @GetMapping("/admin/demensions")
    public Result getDemensionsList(@RequestParam(defaultValue = "1") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        PageBean pageBean = ratingDimensionsService.queryALLByPage(page,size);
        return Result.success(pageBean);
    }

    /**
     * 启用的评分维度
     */
    @GetMapping("/demensions")
    public Result getEnabledDemensions() {
        List<RatingDimensions> list = ratingDimensionsService.queryEnabled();
        return Result.success(list);
    }


}

