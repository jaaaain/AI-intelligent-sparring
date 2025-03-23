package com.jaaaain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatAvgVO {
    private Integer L_avg;// 维度L评分
    private Integer A_avg;// 维度A评分
    private Integer S_avg;// 维度S评分
    private Integer T_avg;// 维度T评分
    private Integer score_avg;// 平均分
}
