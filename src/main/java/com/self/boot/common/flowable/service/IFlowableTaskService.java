package com.self.boot.common.flowable.service;


import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.vo.*;
import org.flowable.idm.api.User;
import org.flowable.task.api.Task;

import java.util.List;

/**
 * 运行时service的顶级接口实现
 *
 * @author jinyun
 */
public interface IFlowableTaskService {

    /**
     * 驳回任意节点 暂时没有考虑子流程
     *
     * @param backTaskVo 参数
     * @return
     */
    void backToStepTask(BackTaskVo backTaskVo);

    /**
     * 获取可驳回节点列表
     *
     * @param taskId            任务id
     * @param processInstanceId 流程实例id
     * @return
     */
    List<FlowNodeVo> getBackNodesByProcessInstanceId(String processInstanceId, String taskId);

    /**
     * 任务前加签 （如果多次加签只能显示第一次前加签的处理人来处理任务）
     *
     * @param addSignTaskVo 参数
     * @return
     */
    String beforeAddSignTask(AddSignTaskVo addSignTaskVo);

    /**
     * 任务后加签
     *
     * @param addSignTaskVo 参数
     * @return
     */
    String afterAddSignTask(AddSignTaskVo addSignTaskVo);

    /**
     * 任务加签
     *
     * @param addSignTaskVo 参数
     * @param flag          true向后加签  false向前加签
     * @return
     */
    String addSignTask(AddSignTaskVo addSignTaskVo, Boolean flag);

    /**
     * 反签收任务
     *
     * @param claimTaskVo 参数
     * @return
     */
    String unClaimTask(ClaimTaskVo claimTaskVo);

    /**
     * 签收任务
     *
     * @param claimTaskVo 参数
     * @return
     */
    String claimTask(ClaimTaskVo claimTaskVo);

    /**
     * 委派任务
     *
     * @param delegateTaskVo 参数
     * @return
     */
    String delegateTask(DelegateTaskVo delegateTaskVo);

    /**
     * 转办
     *
     * @param turnTaskVo 转办任务VO
     * @return 返回信息
     */
    String turnTask(TurnTaskVo turnTaskVo);

    /**
     * 执行任务
     *
     * @param params 参数
     * @return xx
     */
    String complete(CompleteTaskVo params);

    /**
     * 通过任务id获取任务对象
     *
     * @param taskId 任务id
     * @return xx
     */
    Task findTaskById(String taskId);

    /**
     * 查询待办任务列表
     *
     * @param params 参数
     * @param query xx
     * @return xx
     */
    Page<TaskVo> getApplyingTasks(TaskQueryVo params, Query query);

    /**
     * 查询已办任务列表
     *
     * @param params 参数
     * @param query xx
     * @return xx
     */
    Page<TaskVo> getApplyedTasks(TaskQueryVo params, Query query);

    /**
     * 通过流程实例id获取流程实例的待办任务审批人列表
     *
     * @param processInstanceId 流程实例id
     * @return
     */
    List<User> getApprovers(String processInstanceId);

    /**
     * 通过任务id判断当前节点是不是并行网关的节点
     *
     * @param taskId 任务id
     * @return
     */
    boolean checkParallelgatewayNode(String taskId);
}
