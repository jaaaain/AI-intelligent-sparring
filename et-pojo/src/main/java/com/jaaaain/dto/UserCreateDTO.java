package com.jaaaain.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {
    @NotEmpty
    private Integer userId;
    @NotEmpty
    private String userName; // 员工姓名
    @Nullable
    @Range(min = 0, max = 1,message = "只能为0或1")
    private Integer isAdmin; // 是否为管理员
}
