package com.jaaaain.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.jaaaain.dto.UserCreateDTO;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.entity.User;
import com.jaaaain.properties.JwtProPerties;
import com.jaaaain.result.Result;
import com.jaaaain.service.UserService;
import com.jaaaain.utils.JwtUtil;
import com.jaaaain.vo.UserLoginVO;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private UserService userService;

    /**
     * 添加用户接口，只有管理员可以访问
     * @param newUser
     * @return
     */
    @PostMapping("/create")
    public Result addUser(@RequestBody UserCreateDTO newUser) {
        // 添加用户
        userService.addUser(newUser);
        return Result.success();
    }

    /**
     * 删除用户接口，只有管理员可以访问
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return Result.success();
    }

    /**
     * 查询所有用户,只有管理员可以访问
     * @return
     */
    @GetMapping("/list")
    public Result listAllUsers() {
        List<User> userList = userService.queryAllUsers();
        return Result.success(userList);
    }


    /**
     * 修改用户是否为管理员,只有管理员可以访问
     * @param userId
     * @return
     */
    @PutMapping("/toggleAdmin/{userId}")
    public Result toggleAdmin(@PathVariable Integer userId) {
        userService.toggleAdmin(userId);
        return Result.success();
    }


}
