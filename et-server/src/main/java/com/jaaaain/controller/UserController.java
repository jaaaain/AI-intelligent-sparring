package com.jaaaain.controller;


import com.jaaaain.entity.User;
import com.jaaaain.result.Result;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.service.UserService;
import com.jaaaain.utils.JwtUtil;
import com.jaaaain.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProPerties jwtProPerties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("login admin: {}", userLoginDTO);

        // 调用 userService 进行登录验证
        User user = userService.login(userLoginDTO);

        // 令牌内容
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("isAdmin", user.getIsAdmin());  // 修正为 isAdmin

        // 使用 jwtUtil 创建 token
        System.out.println("生成令牌时使用的 secretKey: " + jwtProPerties.getSecretKey());
        String token = jwtUtil.createJWT(
                jwtProPerties.getSecretKey(),
                jwtProPerties.getTtl(),
                claims);

        // 包装传递的对象
        UserLoginVO userLoginVO = UserLoginVO.builder()
                 .userId(user.getUserId())  // 将 Integer 转换为 String
                .username(user.getUsername())
                .isAdmin(user.getIsAdmin())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }
}
