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
        log.info("login user: {}", userLoginDTO);
        User user = userService.login(userLoginDTO);

        // 令牌内容
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("username", user.getUsername());
        claims.put("isAdmin", user.getPassword());

        // 创建 token
        String token = JwtUtil.createJWT(
                jwtProPerties.getSecretKey(),
                jwtProPerties.getTtl(),
                claims);

        // 包装传递的对象
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .isAdmin(user.getIsAdmin())
                .token(token)
                .build();

        return Result.success(userLoginVO);
    }

    /**
     * 用户注册
     * @param user_in
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody User user_in) {
        log.info("register user: {}", user_in);
        userService.insert(user_in);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        log.info("delete user: {}", id);
        userService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/")
    public Result top() {
        log.info("top");
        return Result.success();
    }
}
