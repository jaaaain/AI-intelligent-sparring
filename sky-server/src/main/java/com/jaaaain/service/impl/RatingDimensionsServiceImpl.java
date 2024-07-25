package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.RatingDimensions;
import com.jaaaain.mapper.RatingDimensionsMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.RatingDimensionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储评分维度信息(RatingDimensions)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service("ratingDimensionsService")
public class RatingDimensionsServiceImpl implements RatingDimensionsService {

    @Autowired
    private RatingDimensionsMapper ratingDimensionsMapper;
    /**
     * 通过ID查询单条数据
     * @param dimensionId 主键
     * @return 实例对象
     */
    @Override
    public RatingDimensions queryById(Integer dimensionId) {
        return ratingDimensionsMapper.queryById(dimensionId);
    }

    /**
     * 分页查询
     * @param ratingDimensions 筛选条件
     * @return 查询结果
     */
    @Override
    public PageBean queryByLimit(Integer page, Integer size, RatingDimensions ratingDimensions) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<RatingDimensions> list = ratingDimensionsMapper.queryAllByLimit(ratingDimensions);  // 得到的数据直接为PageBean类型
        Page<RatingDimensions> p = (Page<RatingDimensions>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 新增数据
     * @param ratingDimensions 实例对象
     * @return 实例对象
     */
    @Override
    public RatingDimensions insert(RatingDimensions ratingDimensions) {
        ratingDimensionsMapper.insert(ratingDimensions);
        return ratingDimensions;
    }

    /**
     * 修改数据
     * @param ratingDimensions 实例对象
     * @return 实例对象
     */
    @Override
    public RatingDimensions update(RatingDimensions ratingDimensions) {
        ratingDimensionsMapper.update(ratingDimensions);
        return queryById(ratingDimensions.getDimensionId());
    }

    /**
     * 通过主键删除数据
     * @param dimensionId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer dimensionId) {
        return ratingDimensionsMapper.deleteById(dimensionId) > 0;
    }
}
