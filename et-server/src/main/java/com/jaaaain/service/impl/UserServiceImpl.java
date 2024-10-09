package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.dto.UserCreateDTO;
import com.jaaaain.dto.UserLoginDTO;
import com.jaaaain.entity.User;
import com.jaaaain.exception.BizException;
import com.jaaaain.exception.BizExceptionEnum;
import com.jaaaain.mapper.UserMapper;
import com.jaaaain.properties.JwtProPerties;
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

    @Autowired
    private JwtProPerties jwtProPerties;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {

        Integer userId = userLoginDTO.getUserId();
        String password = userLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        User user = userMapper.queryById(userId);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (user == null) {
            //账号不存在
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        if (!password.equals(user.getPassword())) {
            //密码错误
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);
        }

        return user;
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
     * 新增
     * @param user 实例对象
     */
    @Override
    public void addUser(UserCreateDTO user) {
        userMapper.insert(user);
    }

    /**
     * 修改数据
     * @param user 实例对象
     * @return 实例对象
     */
    @Override
    public User update(User user) {
        userMapper.update(user);
        return userMapper.queryById(user.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     */
    @Override
    public void deleteUserById(Integer id) {
        int rows = userMapper.deleteById(id);
        if (rows == 0) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
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
    public void toggleAdmin(Integer userId) {
        // 查询用户
        User user = userMapper.queryById(userId);
        if (user == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST); // 用户不存在
        }
        // 切换用户权限
        user.setIsAdmin(user.getIsAdmin() == 0 ? 1 : 0);
        // 更新用户状态
        userMapper.update(user);
    }


}
