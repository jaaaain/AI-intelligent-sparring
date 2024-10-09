package com.jaaaain.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScenariosCreateDTO {
    @NotEmpty(message = "场景名称不可为空")
    private String scenarioName;
    @NotEmpty(message="场景描述不可为空")
    @Length(min = 1, max = 255,message = "字数超出")
    private String description;
}
