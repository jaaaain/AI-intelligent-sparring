package com.jaaaain.controller.user;


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

@Slf4j
@RestController
@RequestMapping("/user/user")
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
        String userId = userLoginDTO.getUserId();
        String password = userLoginDTO.getPassword();
        User user = userService.login(userId);
        if (user != null && user.getPassword().equals(password)) {
            HashMap<String, Object> claims = new HashMap<>();
            claims.put("userId", user.getId());
            String token = jwtUtil.createJWT(jwtProPerties.getSecretKey(),jwtProPerties.getTtl(),claims);
            UserLoginVO userLoginVO = new UserLoginVO(user.getId(),user.getUsername(),token);
            log.info("login success");
            return Result.success(userLoginVO);
        } else {
            return Result.error("用户名或密码错误");
        }
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
    public Result delete(@PathVariable Long id) {
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
