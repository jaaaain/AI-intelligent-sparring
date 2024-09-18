package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 存储不同场景的详细信息(Scenarios)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scenarios implements Serializable {
    private Integer scenarioId;// 场景ID
    private String scenarioName;// 场景名称
    private String description;// 场景描述
    private Integer status;// 状态 0:禁用，1:启用
}

