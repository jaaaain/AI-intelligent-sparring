package com.jaaaain.mapper;

import com.jaaaain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * (User)表数据库访问层
 * @since 2024-07-17 15:24:50
 */
@Mapper
public interface UserMapper {
    /**
     * 通过ID查询单条数据
     * @param userid 主键
     * @return 实例对象
     */
    User queryById(String userid);

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 条件查询指定行数据
     * @param user 查询条件
     * @return 对象列表
     */
    List<User> queryAllByLimit(User user);

    /**
     * 新增数据
     * @param user 实例对象
     * @return 影响行数
     */
    int insert(User user);

    /**
     * 修改数据
     * @param user 实例对象
     * @return 影响行数
     */
    int update(User user);

    /**
     * 通过username和password查询用户
     * @param username,password
     * @return
     */
    User queryByUsername(String username);
}

