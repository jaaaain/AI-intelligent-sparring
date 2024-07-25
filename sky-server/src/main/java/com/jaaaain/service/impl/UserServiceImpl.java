package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.User;
import com.jaaaain.mapper.UserMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储系统中所有用户的信息(Users)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * 通过ID查询单条数据
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public User queryById(String userId) {
        return userMapper.queryById(userId);
    }

    /**
     * 分页查询
     * @param user 筛选条件
     * @return 查询结果
     */
    @Override
    public PageBean queryByLimit(Integer page, Integer size, User user) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<User> list = userMapper.queryAllByLimit(user);  // 得到的数据直接为PageBean类型
        Page<User> p = (Page<User>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 新增数据
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User insert(User user) {
        userMapper.insert(user);
        return user;
    }

    /**
     * 修改数据
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        userMapper.update(user);
        return queryById(user.getUserId());
    }

    /**
     * 通过主键删除数据
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userId) {
        return userMapper.deleteById(userId) > 0;
    }
}
