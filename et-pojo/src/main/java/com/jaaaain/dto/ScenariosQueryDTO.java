package com.jaaaain.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenariosQueryDTO {
    Integer page = 1;
    Integer size = 10;

    @Nullable
    @Range(min = 0, max = 1,message = "只能为0或1") // 默认空值不校验
    Integer status;

    String searchBy;
}
