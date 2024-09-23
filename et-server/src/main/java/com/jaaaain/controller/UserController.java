package com.jaaaain.controller;


import com.jaaaain.entity.User;
import com.jaaaain.result.Result;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.service.UserService;
import com.jaaaain.utils.JwtUtil;
import com.jaaaain.vo.UserLoginVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

        // 调用 userService 进行登录验证
        User user = userService.login(userLoginDTO);

        // 检查用户是否存在
        if (user == null) {
            return Result.error("用户名或密码错误");
        }

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
                 .userId(String.valueOf(user.getUserId()))  // 将 Integer 转换为 String
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



    // 添加用户接口，只有管理员可以访问
    @PostMapping("/addUser")
    public Result addUser(@RequestHeader("Authorization") String token, @RequestBody User newUser) {
         // 打印获取到的 token
        System.out.println("收到的 Authorization Header: " + token);

        // 去掉 Bearer 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
        }

        // 打印去掉 Bearer 后的 token
        System.out.println("解析前的 Token: " + token);

        // 从 JWT 中解析出管理员信息
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token, jwtProPerties.getSecretKey());
            // 直接在控制台输出解析的 claims 和 isAdmin 的值
            System.out.println("解析后的 claims: " + claims.toString()); // 使用 toString() 输出 claims
        } catch (Exception e) {
            System.out.println("Token 解析失败，错误信息: " + e.getMessage());
            return Result.error("无效的 Token");
        }


         // 日志输出解析的 claims 和 isAdmin 的值
        System.out.println("解析后的 claims: {}"+claims); // 这里输出整个 claims
        Object isAdminObj = claims.get("isAdmin");
        System.out.println("isAdmin 值: {}"+isAdminObj); // 这里输出 isAdmin 的值

        // 处理 isAdmin 字段的数据类型
        Boolean isAdmin;

        if (isAdminObj instanceof Integer) {
            // 将数据库中存储的 int 类型转换为 Boolean
            isAdmin = ((Integer) isAdminObj) == 1;
        } else if (isAdminObj instanceof Boolean) {
            isAdmin = (Boolean) isAdminObj;
        } else {
            return Result.error("无权限操作，权限解析失败");
        }

        // 校验是否为管理员
        if (!isAdmin) {
            return Result.error("无权限操作，只有管理员可以添加用户");
        }

        // 校验新用户信息
        if (newUser.getUsername() == null || newUser.getPassword() == null) {
            return Result.error("用户名或密码不能为空");
        }

        // 添加用户
        userService.insert(newUser);

        return Result.success("用户添加成功");
    }

     // 删除用户接口，只有管理员可以访问
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id, @RequestHeader("Authorization") String token) {

        // 去掉 Bearer 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
        }


        // 从 JWT 中解析出管理员信息
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token, jwtProPerties.getSecretKey());
            // 直接在控制台输出解析的 claims 和 isAdmin 的值
            System.out.println("解析后的 claims: " + claims.toString()); // 使用 toString() 输出 claims
        } catch (Exception e) {
            System.out.println("Token 解析失败，错误信息: " + e.getMessage());
            return Result.error("无效的 Token");
        }

         // 解析的 claims 和 isAdmin 的值
        Object isAdminObj = claims.get("isAdmin");


        // 处理 isAdmin 字段的数据类型
        Boolean isAdmin;

        if (isAdminObj instanceof Integer) {
            // 将数据库中存储的 int 类型转换为 Boolean
            isAdmin = ((Integer) isAdminObj) == 1;
        } else if (isAdminObj instanceof Boolean) {
            isAdmin = (Boolean) isAdminObj;
        } else {
            return Result.error("无权限操作，权限解析失败");
        }

        // 校验是否为管理员
        if (!isAdmin) {
            return Result.error("无权限操作，只有管理员可以添加用户");
        }
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return Result.success("用户删除成功");
        } else {
            return Result.error("用户删除失败或用户不存在");
        }
    }

     // 查询所有用户
    @GetMapping("/list")
    public Result listAllUsers() {
        List<User> userList = userService.queryAllUsers();
        return Result.success(userList);
    }

   // 修改用户是否为管理员,只有管理员可以访问
    @PutMapping("/toggleAdmin/{userId}")
    public Result toggleAdmin(@RequestHeader("Authorization") String token, @PathVariable Integer userId) {
        // 去掉 Bearer 前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // 去掉 "Bearer " 前缀
        }
        // 从 JWT 中解析出管理员信息
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token, jwtProPerties.getSecretKey());
            // 直接在控制台输出解析的 claims 和 isAdmin 的值
            System.out.println("解析后的 claims: " + claims.toString()); // 使用 toString() 输出 claims
        } catch (Exception e) {
            System.out.println("Token 解析失败，错误信息: " + e.getMessage());
            return Result.error("无效的 Token");
        }

         // 解析的 claims 和 isAdmin 的值
        Object isAdminObj = claims.get("isAdmin");


        // 处理 isAdmin 字段的数据类型
        Boolean isAdmin;

        if (isAdminObj instanceof Integer) {
            // 将数据库中存储的 int 类型转换为 Boolean
            isAdmin = ((Integer) isAdminObj) == 1;
        } else if (isAdminObj instanceof Boolean) {
            isAdmin = (Boolean) isAdminObj;
        } else {
            return Result.error("无权限操作，权限解析失败");
        }

        // 校验是否为管理员
        if (!isAdmin) {
            return Result.error("无权限操作，只有管理员可以添加用户");
        }
        boolean success = userService.toggleAdmin(userId);
        if (success) {
            return Result.success();
        } else {
            return Result.error("Failed to update admin status.");
        }
    }

    @GetMapping("/")
    public Result top() {
        log.info("top");
        return Result.success();
    }
}
