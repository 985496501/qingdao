package com.self.boot.common.flowable.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.self.boot.common.flowable.cmd.DeleteFlowableProcessInstanceCmd;
import com.self.boot.common.flowable.conf.FlowProcessDiagramGenerator;
import com.self.boot.common.flowable.core.CommentEnum;
import com.self.boot.common.flowable.core.FlowConstant;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.SuspendState;
import com.self.boot.common.flowable.dao.IFlowableProcessInstanceDao;
import com.self.boot.common.flowable.service.BaseProcessService;
import com.self.boot.common.flowable.service.IFlowableBpmnModelService;
import com.self.boot.common.flowable.service.IFlowableProcessInstanceService;
import com.self.boot.common.flowable.service.IFlowableTaskService;
import com.self.boot.common.flowable.util.StrUtil;
import com.self.boot.common.flowable.vo.*;
import lombok.RequiredArgsConstructor;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.Activity;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EndEvent;
import org.flowable.common.engine.impl.util.IoUtil;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.User;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程实例的默认实现
 *
 * @author jinyun
 */
@Service
@RequiredArgsConstructor
public class FlowableProcessInstanceServiceImpl extends BaseProcessService implements IFlowableProcessInstanceService {
    private final IFlowableBpmnModelService flowableBpmnModelService;
    private final IFlowableProcessInstanceDao flowableProcessInstanceDao;
    private final FlowProcessDiagramGenerator flowProcessDiagramGenerator;
    private final IFlowableTaskService flowableTaskService;

    @Override
    public Page<ProcessInstanceVo> getPagerModel(ProcessInstanceQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        Page<ProcessInstanceVo> page = flowableProcessInstanceDao.getPagerModel(params);
        page.forEach(this::setStateApprover);
        return page;
    }

    /**
     * 设置状态和审批人
     *
     * @param processInstanceVo 参数
     */
    private void setStateApprover(ProcessInstanceVo processInstanceVo) {
        if (processInstanceVo.getEndTime() == null) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(processInstanceVo.getProcessInstanceId())
                    .singleResult();
            if (processInstance.isSuspended()) {
                processInstanceVo.setSuspensionState(FlowConstant.SUSPENSION_STATE);
            } else {
                processInstanceVo.setSuspensionState(FlowConstant.ACTIVATE_STATE);
            }
        }
        List<User> approvers = flowableTaskService.getApprovers(processInstanceVo.getProcessInstanceId());
        String userNames = this.createApprovers(approvers);
        processInstanceVo.setApprover(userNames);
    }

    /**
     * 组合审批人显示名称
     *
     * @param approvers 审批人列表
     * @return
     */
    private String createApprovers(List<User> approvers) {
        if (!CollectionUtils.isEmpty(approvers)) {
            StringBuffer approverstr = new StringBuffer();
            StringBuffer finalApproverstr = approverstr;
            approvers.forEach(user -> {
                finalApproverstr.append(user.getDisplayName()).append(";");
            });
            if (approverstr.length() > 0) {
                approverstr = approverstr.deleteCharAt(approverstr.length() - 1);
            }
            return approverstr.toString();
        }
        return null;
    }

    @Override
    public ProcessInstance startProcessInstanceByKey(StartProcessInstanceVo params) {
        if (StrUtil.allNotEmpty(params.getProcessDefinitionKey(), params.getBusinessKey(), params.getSystemSn())) {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(params.getProcessDefinitionKey()).latestVersion().singleResult();

//            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
//                    .processDefinitionKey(params.getProcessDefinitionKey()).latestVersion().list();
//
//            ProcessDefinition processDefinition = list.get(0);
            if (processDefinition != null && processDefinition.isSuspended()) {
                throw new RuntimeException("改流程已经挂起");
            }

            /**
             * 1、设置变量
             * 1.1、设置提交人字段为空字符串让其自动跳过
             * 1.2、设置可以自动跳过
             * 1.3、汇报线的参数设置
             */
            //1.1、设置提交人字段为空字符串让其自动跳过
//            params.getVariables().put(FlowConstant.FLOW_SUBMITTER_VAR, "");
            //1.2、设置可以自动跳过
//            params.getVariables().put(FlowConstant.FLOWABLE_SKIP_EXPRESSION_ENABLED, true);



            // TODO 1.3、汇报线的参数设置

            //2、当我们流程创建人和发起人
            String creator = params.getCreator();
            if (!StringUtils.hasLength(creator)) {
                creator = params.getCurrentUserCode();
                params.setCreator(creator);
            }

            //3.启动流程
            identityService.setAuthenticatedUserId(creator);
            ProcessInstance processInstance = runtimeService.createProcessInstanceBuilder()
                    .processDefinitionKey(params.getProcessDefinitionKey().trim())
                    .name(params.getFormName().trim())
                    .businessKey(params.getBusinessKey().trim())
                    .variables(params.getVariables())
                    .tenantId(params.getSystemSn().trim())
                    .start();

            //4.添加审批记录
            this.addComment(params.getCurrentUserCode(), processInstance.getProcessInstanceId(),
                    CommentEnum.TJ.toString(), params.getFormName() + "提交");
            //5.TODO 推送消息数据
            return processInstance;
        } else {
            throw new RuntimeException("请填写 这三个字段 ProcessDefinitionKey,BusinessKey,SystemSn");
        }
    }

    @Override
    public Page<ProcessInstanceVo> getMyProcessInstances(ProcessInstanceQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        if (StringUtils.hasLength(params.getUserCode())) {
            Page<ProcessInstanceVo> myProcesses = flowableProcessInstanceDao.getPagerModel(params);
            myProcesses.forEach(this::setStateApprover);
            return myProcesses;
        }

        return null;
    }

    @Override
    public byte[] createImage(String processInstanceId) {
        //1.获取当前的流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = null;
        List<String> activeActivityIds = new ArrayList<>();
        List<String> highLightedFlows = new ArrayList<>();
        //2.获取所有的历史轨迹线对象
        List<HistoricActivityInstance> historicSquenceFlows = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(processInstanceId).activityType(BpmnXMLConstants.ELEMENT_SEQUENCE_FLOW).list();
        historicSquenceFlows.forEach(historicActivityInstance -> highLightedFlows.add(historicActivityInstance.getActivityId()));
        //3. 获取流程定义id和高亮的节点id
        if (processInstance != null) {
            //3.1. 正在运行的流程实例
            processDefinitionId = processInstance.getProcessDefinitionId();
            activeActivityIds = runtimeService.getActiveActivityIds(processInstanceId);
        } else {
            //3.2. 已经结束的流程实例
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
            //3.3. 获取结束节点列表
            List<HistoricActivityInstance> historicEnds = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId).activityType(BpmnXMLConstants.ELEMENT_EVENT_END).list();
            List<String> finalActiveActivityIds = activeActivityIds;
            historicEnds.forEach(historicActivityInstance -> finalActiveActivityIds.add(historicActivityInstance.getActivityId()));
        }
        //4. 获取bpmnModel对象
        BpmnModel bpmnModel = flowableBpmnModelService.getBpmnModelByProcessDefId(processDefinitionId);

        //5. 生成图片流
        InputStream inputStream = flowProcessDiagramGenerator.generateDiagram(bpmnModel, activeActivityIds, highLightedFlows);
        //6. 转化成byte便于网络传输
        return IoUtil.readInputStream(inputStream, "image inputStream name");
    }

    @Override
    public void deleteProcessInstanceById(String processInstanceId) {
        long count = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count();
        if (count > 0) {
            DeleteFlowableProcessInstanceCmd cmd = new DeleteFlowableProcessInstanceCmd(processInstanceId, "删除流程实例", true);
            managementService.executeCommand(cmd);
        } else {
            historyService.deleteHistoricProcessInstance(processInstanceId);
        }
    }

    @Override
    public void suspendOrActivateProcessInstanceById(String processInstanceId, SuspendState state) {
        if (state == SuspendState.SUSPEND) {
            runtimeService.suspendProcessInstanceById(processInstanceId);
        } else {
            runtimeService.activateProcessInstanceById(processInstanceId);
        }
    }

    @Override
    public void stopProcessInstanceById(EndProcessVo endVo) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(endVo.getProcessInstanceId()).singleResult();
        if (processInstance != null) {
            //1、添加审批记录
            this.addComment(endVo.getUserCode(), endVo.getProcessInstanceId(), CommentEnum.LCZZ.toString(),
                    endVo.getMessage());
            List<EndEvent> endNodes = flowableBpmnModelService.findEndFlowElement(processInstance.getProcessDefinitionId());
            String endId = endNodes.get(0).getId();
            String processInstanceId = endVo.getProcessInstanceId();
            //2、执行终止
            List<Execution> executions = runtimeService.createExecutionQuery().parentId(processInstanceId).list();
            List<String> executionIds = new ArrayList<>();
            executions.forEach(execution -> executionIds.add(execution.getId()));
            this.moveExecutionsToSingleActivityId(executionIds, endId);
        } else {
            throw new RuntimeException("不存在运行的流程实例,请确认!");
        }

    }

    @Override
    public void revokeProcess(RevokeProcessVo revokeVo) {
        if (StringUtils.hasLength(revokeVo.getProcessInstanceId())) {
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(revokeVo.getProcessInstanceId()).singleResult();
            if (processInstance != null) {
                //1.添加撤回意见
                this.addComment(revokeVo.getUserCode(), revokeVo.getProcessInstanceId(), CommentEnum.CH.toString(), revokeVo.getMessage());
                //2.设置提交人
                runtimeService.setVariable(revokeVo.getProcessInstanceId(), FlowConstant.FLOW_SUBMITTER_VAR, processInstance.getStartUserId());
                //3.执行撤回
                Activity disActivity = flowableBpmnModelService.findActivityByName(processInstance.getProcessDefinitionId(), FlowConstant.FLOW_SUBMITTER);
                //4.删除运行和历史的节点信息
                this.deleteActivity(disActivity.getId(), revokeVo.getProcessInstanceId());
                //5.执行跳转
                List<Execution> executions = runtimeService.createExecutionQuery().parentId(revokeVo.getProcessInstanceId()).list();
                List<String> executionIds = new ArrayList<>();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                this.moveExecutionsToSingleActivityId(executionIds, disActivity.getId());
            }
        } else {
            throw new RuntimeException("流程实例id不能为空!");
        }
    }
}
