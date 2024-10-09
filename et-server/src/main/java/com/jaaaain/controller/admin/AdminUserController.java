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

    // 添加用户接口，只有管理员可以访问
    @PostMapping("/create")
    public Result addUser(@RequestBody UserCreateDTO newUser) {
        // 添加用户
        userService.addUser(newUser);
        return Result.success();
    }
    @RequestMapping("/download")
    public String fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName){
        File file = new File("D:/000Projects/JAVA/emp-train/"+ fileName);
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}",e);
            return "下载失败";
        }
        return "下载成功";
    }
    @RequestMapping("/upload")
    public Result httpUpload(@RequestParam("file") MultipartFile file){
        String fileName = file.getOriginalFilename();  // 文件名
        File dest = new File("D:/000Projects/JAVA/emp-train/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            log.error("{}",e);
            return Result.error("上传失败");
        }
        return Result.success();
    }


     // 删除用户接口，只有管理员可以访问
    @DeleteMapping("/delete/{id}")
    public Result deleteUser(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return Result.success();
    }

     // 查询所有用户
    @GetMapping("/list")
    public Result listAllUsers() {
        List<User> userList = userService.queryAllUsers();
        return Result.success(userList);
    }

   // 修改用户是否为管理员,只有管理员可以访问
    @PutMapping("/toggleAdmin/{userId}")
    public Result toggleAdmin(@PathVariable Integer userId) {
        userService.toggleAdmin(userId);
        return Result.success();
    }
}
