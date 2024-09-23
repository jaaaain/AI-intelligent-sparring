package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.constant.MessageConstant;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.entity.User;
import com.jaaaain.exception.AccountNotFoundException;
import com.jaaaain.exception.PasswordErrorException;
import com.jaaaain.mapper.UserMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

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
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {

        String userId = userLoginDTO.getUserId();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.queryById(userId);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //3、返回实体对象
        return user;
    }

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
        return queryById(String.valueOf(user.getUserId())); // 将 Integer 转为 String
    }

    /**
     * 通过主键删除数据
     * @param id 主键
     * @return 是否成功
     */


    @Override
    public boolean deleteUserById(Integer id) {
        int rows = userMapper.deleteById(id);
        return rows > 0;
    }


    /**
     * 查询所有用户
     * @return 用户列表
     */
    @Override
    public List<User> queryAllUsers() {
        return userMapper.queryAllUsers();
    }

    @Override
    public boolean toggleAdmin(Integer userId) {
        // 查询用户
        User user = userMapper.queryById(userId);
        if (user == null) {
            return false; // 用户不存在
        }

        // 切换用户权限
        user.setIsAdmin(user.getIsAdmin() == 0 ? 1 : 0);

        // 更新用户状态
        return userMapper.update(user) > 0;
    }


}
