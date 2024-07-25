package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.mapper.ScenariosMapper;
import com.jaaaain.result.PageBean;
import com.jaaaain.service.ScenariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 存储不同场景的详细信息(Scenarios)表服务实现类
 * @since 2024-07-23 16:34:25
 */
@Service("scenariosService")
public class ScenariosServiceImpl implements ScenariosService {

    @Autowired
    private ScenariosMapper scenariosMapper;
    /**
     * 通过ID查询单条数据
     * @param scenarioId 主键
     * @return 实例对象
     */
    @Override
    public Scenarios queryById(Integer scenarioId) {
        return scenariosMapper.queryById(scenarioId);
    }

    /**
     * 分页查询
     * @param scenarios 筛选条件
     * @return 查询结果
     */
    @Override
    public PageBean queryByLimit(Integer page, Integer size, Scenarios scenarios) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<Scenarios> list = scenariosMapper.queryAllByLimit(scenarios);  // 得到的数据直接为PageBean类型
        Page<Scenarios> p = (Page<Scenarios>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }

    /**
     * 新增数据
     * @param scenarios 实例对象
     * @return 实例对象
     */
    @Override
    public Scenarios insert(Scenarios scenarios) {
        scenariosMapper.insert(scenarios);
        return scenarios;
    }

    /**
     * 修改数据
     * @param scenarios 实例对象
     * @return 实例对象
     */
    @Override
    public Scenarios update(Scenarios scenarios) {
        scenariosMapper.update(scenarios);
        return queryById(scenarios.getScenarioId());
    }

    /**
     * 通过主键删除数据
     * @param scenarioId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer scenarioId) {
        return scenariosMapper.deleteById(scenarioId) > 0;
    }
}
