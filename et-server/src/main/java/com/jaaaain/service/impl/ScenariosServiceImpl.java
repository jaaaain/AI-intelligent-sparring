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
    public Scenarios queryById(int scenarioId) {
        return scenariosMapper.queryById(scenarioId);
    }

    /**
     * 普通分页查询
     * @param
     * @return 查询结果
     */
    @Override
    public PageBean queryALLByPage(int page, int size) {
        PageHelper.startPage(page, size); // 将下一条搜索改为查count和limit两条
        List<Scenarios> list = scenariosMapper.queryAll();  // 得到的数据直接为PageBean类型
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
     * 获取启用的评分维度信息
     * @param
     * @return 查询结果
     */
    @Override
    public List<Scenarios> queryEnabled() {
        return scenariosMapper.queryEnabled();
    }
}
