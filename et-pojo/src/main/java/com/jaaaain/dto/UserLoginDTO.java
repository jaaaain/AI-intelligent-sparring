package com.jaaaain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    @NotEmpty
    private Integer userId; // 员工id
    @NotEmpty
    private String password; // 密码
}
