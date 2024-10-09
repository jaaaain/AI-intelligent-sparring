package com.jaaaain.service;

import com.jaaaain.dto.UserCreateDTO;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.entity.User;
import com.jaaaain.result.PageBean;

import java.util.List;

/**
 * 存储系统中所有用户的信息(Users)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface UserService {

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    User login(UserLoginDTO userLoginDTO);

    /**
     * 分页查询
     * @param user 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, User user);

    /**
     * 新增数据
     *
     * @param user 实例对象
     */
    void addUser(UserCreateDTO user);

    /**
     * 修改数据
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     */
     // 删除用户
    void deleteUserById(Integer id);
    /**
     * 查询所有用户
     * @return 用户列表
     */
    List<User> queryAllUsers();

    // 根据用户ID切换isAdmin状态
    void toggleAdmin(Integer userId);

}
