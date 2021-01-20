package com.self.boot.common.flowable.service;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.vo.*;
import org.flowable.engine.runtime.ProcessInstance;

/**
 * 工作流实例的顶级接口
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public interface IFlowableProcessInstanceService {

    /**
     * 启动流程
     *
     * @param startProcessInstanceVo 开启参数
     * @return 流程实例
     */
    ProcessInstance startProcessInstanceByKey(StartProcessInstanceVo startProcessInstanceVo);

    /**
     * 查询流程实例列表
     *
     * @param params 参数
     * @param query  分页参数
     * @return
     */
    Page<ProcessInstanceVo> getPagerModel(ProcessInstanceQueryVo params, Query query);

    /**
     * 查询我发起的流程实例
     *
     * @param params 参数
     * @param query  分页参数
     * @return
     */
    Page<ProcessInstanceVo> getMyProcessInstances(ProcessInstanceQueryVo params, Query query);

    /**
     * 获取流程图图片
     *
     * @param processInstanceId 流程实例id
     * @return byte[]
     */
    byte[] createImage(String processInstanceId);

    /**
     * 删除流程实例
     *
     * @param processInstanceId 流程实例id
     * @return
     */
    void deleteProcessInstanceById(String processInstanceId);

    /**
     * 激活流程定义
     *
     * @param processInstanceId 流程实例id
     * @param suspensionState   2激活 1挂起
     * @return
     */
    void suspendOrActivateProcessInstanceById(String processInstanceId, Integer suspensionState);

    /**
     * 终止流程
     *
     * @param endVo 参数
     * @return
     */
    void stopProcessInstanceById(EndProcessVo endVo);

    /**
     * 撤回流程
     *
     * @param revokeVo 参数
     * @return
     */
    void revokeProcess(RevokeProcessVo revokeVo);
}
