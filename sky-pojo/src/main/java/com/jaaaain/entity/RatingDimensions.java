package com.jaaaain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 存储评分维度信息(RatingDimensions)实体类
 * @since 2024-07-23 14:20:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDimensions implements Serializable {
    private Integer dimensionId;// 评分维度ID
    private String dimensionName;// 维度名称
    private String description;// 维度描述
    private Integer status;// 状态 0:禁用，1:启用
}

