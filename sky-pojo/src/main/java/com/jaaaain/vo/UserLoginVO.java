package com.jaaaain.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO implements Serializable {
    private String userId;
    private int isAdmin;
    private String token;
}