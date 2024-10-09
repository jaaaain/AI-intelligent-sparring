package com.jaaaain.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.jaaaain.context.BaseContext;
import com.jaaaain.dto.ScenariosCreateDTO;
import com.jaaaain.dto.ScenariosQueryDTO;
import com.jaaaain.entity.Scenarios;
import com.jaaaain.exception.BizException;
import com.jaaaain.exception.BizExceptionEnum;
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

    @Override
    public Scenarios queryById(Integer scenarioId) {
        return scenariosMapper.queryById(scenarioId);
    }

    /**
     * 普通分页查询
     * @param
     * @return 查询结果
     */
    @Override
    public PageBean queryForScenarios(ScenariosQueryDTO scenariosQueryDTO) {
        if (BaseContext.getCurrentRole().equals(0)
                && scenariosQueryDTO.getStatus().equals(0)) { // 普通员工不可查看隐藏的场景
            throw new BizException(BizExceptionEnum.NOT_ADMIN);
        }
        PageHelper.startPage(scenariosQueryDTO.getPage(), scenariosQueryDTO.getSize()); // 将下一条搜索改为查count和limit两条
        List<Scenarios> list = scenariosMapper.queryList(scenariosQueryDTO);  // 得到的数据直接为PageBean类型
        Page<Scenarios> p = (Page<Scenarios>) list;  // 强制类型转换
        PageBean pageBean = new PageBean(p.getTotal(),p.getResult());
        return pageBean;
    }
    /**
     * 新增数据
     * @param scenariosCreateDTO
     */
    @Override
    public void newScenarios(ScenariosCreateDTO scenariosCreateDTO) {
        if(!scenariosMapper.queryByName(scenariosCreateDTO.getScenarioName()).isEmpty()){ // 已有该场景
            System.out.println("已有场景："+scenariosCreateDTO.getScenarioName());
            throw new BizException(BizExceptionEnum.SCENARIO_EXIST);
        }
        scenariosMapper.insert(scenariosCreateDTO);
    }

    /**
     * 切换场景显示状态-设置status为1-status
     * 条件：场景id存在且不为-1
     * @param scenarioId
     * @return 实例对象
     */
    @Override
    public Scenarios toggleStatus(Integer scenarioId) {
        Scenarios scenarios = scenariosMapper.queryById(scenarioId);
        if(scenarios==null || scenarios.getStatus()<0){
            throw new BizException(BizExceptionEnum.SCENARIO_NOT_EXIST);
        } else{
            scenarios.setStatus(1-scenarios.getStatus());
            scenariosMapper.update(scenarios);
        }
        return scenarios;
    }

    /**
     * 更新场景描述-更新description
     * 条件：场景id存在且不为-1
     * @param scenarioId
     * @param description
     * @return
     */
    @Override
    public Scenarios updateDescription(Integer scenarioId, String description) {
        Scenarios scenarios = scenariosMapper.queryById(scenarioId);
        if(scenarios==null || scenarios.getStatus()<0){
            throw new BizException(BizExceptionEnum.SCENARIO_NOT_EXIST);
        } else if (!scenarios.getDescription().equals(description)) {
            throw new BizException(BizExceptionEnum.DESCRIPTION_NOT_CHANGE);
        } else{
            scenarios.setDescription(description);
            scenariosMapper.update(scenarios);
        }
        return scenarios;
    }

    /**
     * 删除场景-设置status为-1
     * 条件：场景id存在且不为-1
     * @param scenarioId
     */
    @Override
    public void deleteScenario(Integer scenarioId) {
        Scenarios scenarios = scenariosMapper.queryById(scenarioId);
        if(scenarios==null || scenarios.getStatus()<0){
            throw new BizException(BizExceptionEnum.SCENARIO_NOT_EXIST);
        } else{
            scenarios.setStatus(-1);
            scenariosMapper.update(scenarios);
        }
    }
}
