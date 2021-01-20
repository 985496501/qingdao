package com.self.boot.common.flowable.service;


import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.SuspendState;
import com.self.boot.common.flowable.vo.ProcessDefinitionQueryVo;
import com.self.boot.common.flowable.vo.ProcessDefinitionVo;

/**
 * procession definition superinterface.
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public interface IFlowableProcessDefinitionService {

    /**
     * 通过条件查询流程定义
     *
     * @param params 参数
     * @param query  参数
     * @return PagerModel
     */
    Page<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params, Query query);

    /**
     * 通过流程定义id获取流程定义的信息
     *
     * @param processDefinitionId 流程定义id
     * @return ProcessDefinitionVo
     */
    ProcessDefinitionVo getById(String processDefinitionId);

    /**
     * 挂起流程定义
     * 一旦挂起 就不能生成实例
     *
     * @param processDefinitionId 流程定义id
     * @param state     状态1挂起 2激活
     * @return true or false
     */
    void suspendOrActivateProcessDefinitionById(String processDefinitionId, SuspendState state);
}
