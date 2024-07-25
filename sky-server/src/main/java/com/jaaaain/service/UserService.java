package com.jaaaain.service;

import com.jaaaain.entity.User;
import com.jaaaain.result.PageBean;

/**
 * 存储系统中所有用户的信息(Users)表服务接口
 * @since 2024-07-23 16:34:25
 */
public interface UserService {
    /**
     * 通过ID查询单条数据
     * @param userId 主键
     * @return 实例对象
     */
    User queryById(String userId);

    /**
     * 分页查询
     * @param user 筛选条件
     * @return 查询结果
     */
    PageBean queryByLimit(Integer page, Integer size, User user);

    /**
     * 新增数据
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user);

    /**
     * 修改数据
     * @param user 实例对象
     * @return 实例对象
     */
    User update(User user);

    /**
     * 通过主键删除数据
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(String userId);
}
