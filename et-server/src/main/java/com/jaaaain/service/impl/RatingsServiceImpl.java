package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.Ratings;
import com.jaaaain.mapper.RatingsMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.RatingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储对话的评分结果(Ratings)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service("ratingsService")
public class RatingsServiceImpl implements RatingsService {

    @Autowired
    private RatingsMapper ratingsMapper;
    /**
     * 通过ID查询单条数据
     * @param ratingId 主键
     * @return 实例对象
     */
    @Override
    public Ratings queryById(Integer ratingId) {
        return ratingsMapper.queryById(ratingId);
    }

    /**
     * 新增数据
     * @param ratings 实例对象
     * @return 实例对象
     */
    @Override
    public Ratings insert(Ratings ratings) {
        ratingsMapper.insert(ratings);
        return ratings;
    }

    /**
     * 修改数据
     * @param ratings 实例对象
     * @return 实例对象
     */
    @Override
    public Ratings update(Ratings ratings) {
        ratingsMapper.update(ratings);
        return queryById(ratings.getRatingId());
    }

    /**
     * 通过主键删除数据
     * @param ratingId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer ratingId) {
        return ratingsMapper.deleteById(ratingId) > 0;
    }
}
