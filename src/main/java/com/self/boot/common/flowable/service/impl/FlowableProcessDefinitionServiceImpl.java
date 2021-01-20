package com.self.boot.common.flowable.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.SuspendState;
import com.self.boot.common.flowable.dao.IFlowableProcessDefinitionDao;
import com.self.boot.common.flowable.service.BaseProcessService;
import com.self.boot.common.flowable.service.IFlowableProcessDefinitionService;
import com.self.boot.common.flowable.vo.ProcessDefinitionQueryVo;
import com.self.boot.common.flowable.vo.ProcessDefinitionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 流程定义的默认实现
 *
 * @author jinyun
 */
@Service
@RequiredArgsConstructor
public class FlowableProcessDefinitionServiceImpl extends BaseProcessService implements IFlowableProcessDefinitionService {

    private final IFlowableProcessDefinitionDao flowableProcessDefinitionDao;

    @Override
    public Page<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        return flowableProcessDefinitionDao.getPagerModel(params);
    }

    @Override
    public ProcessDefinitionVo getById(String processDefinitionId) {
        return flowableProcessDefinitionDao.getById(processDefinitionId);
    }

    @Override
    public void suspendOrActivateProcessDefinitionById(String processDefinitionId, SuspendState state) {
        if (SuspendState.SUSPEND == state) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
        } else {
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        }
    }

}
