package com.self.boot.common.flowable.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.self.boot.common.flowable.core.CommentEnum;
import com.self.boot.common.flowable.core.FlowConstant;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.dao.IFlowableTaskDao;
import com.self.boot.common.flowable.service.BaseProcessService;
import com.self.boot.common.flowable.service.IFlowableBpmnModelService;
import com.self.boot.common.flowable.service.IFlowableTaskService;
import com.self.boot.common.flowable.util.StrUtil;
import com.self.boot.common.flowable.vo.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.constants.BpmnXMLConstants;
import org.flowable.bpmn.model.FlowNode;
import org.flowable.engine.runtime.ActivityInstance;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.identitylink.api.IdentityLink;
import org.flowable.identitylink.api.IdentityLinkType;
import org.flowable.idm.api.User;
import org.flowable.task.api.DelegationState;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;
import org.flowable.task.service.impl.persistence.entity.TaskEntityImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * task service
 *
 * @author jinyun
 */
@Service
@RequiredArgsConstructor
public class FlowableTaskServiceImpl extends BaseProcessService implements IFlowableTaskService {
    private final IFlowableTaskDao flowableTaskDao;
    private final IFlowableBpmnModelService flowableBpmnModelService;

    @Override
    public boolean checkParallelgatewayNode(String taskId) {
        boolean flag = false;
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String executionId = task.getExecutionId();
        Execution execution = runtimeService.createExecutionQuery().executionId(executionId).singleResult();
        String pExecutionId = execution.getParentId();
        Execution pExecution = runtimeService.createExecutionQuery().executionId(pExecutionId).singleResult();
        if (pExecution != null) {
            String ppExecutionId = pExecution.getParentId();
            long count = runtimeService.createExecutionQuery().executionId(ppExecutionId).count();
            if (count == 0) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public void backToStepTask(BackTaskVo backTaskVo) {
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(backTaskVo.getTaskId()).singleResult();
        //1.把当前的节点设置为空
        if (taskEntity != null) {
            //2.设置审批人
            taskEntity.setAssignee(backTaskVo.getUserCode());
            taskService.saveTask(taskEntity);
            //3.添加驳回意见
            this.addComment(backTaskVo.getTaskId(), backTaskVo.getUserCode(), backTaskVo.getProcessInstanceId(),
                    CommentEnum.BH.toString(), backTaskVo.getMessage());
            //4.处理提交人节点
            FlowNode distActivity = flowableBpmnModelService.findFlowNodeByActivityId(taskEntity.getProcessDefinitionId(), backTaskVo.getDistFlowElementId());
            if (distActivity != null) {
                if (FlowConstant.FLOW_SUBMITTER.equals(distActivity.getName())) {
                    ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(taskEntity.getProcessInstanceId()).singleResult();
                    runtimeService.setVariable(backTaskVo.getProcessInstanceId(), FlowConstant.FLOW_SUBMITTER_VAR, processInstance.getStartUserId());
                }
            }
            //5.删除节点
            this.deleteActivity(backTaskVo.getDistFlowElementId(), taskEntity.getProcessInstanceId());
            List<String> executionIds = new ArrayList<>();
            //6.判断节点是不是子流程内部的节点
            if (flowableBpmnModelService.checkActivitySubprocessByActivityId(taskEntity.getProcessDefinitionId(),
                    backTaskVo.getDistFlowElementId())
                    && flowableBpmnModelService.checkActivitySubprocessByActivityId(taskEntity.getProcessDefinitionId(),
                    taskEntity.getTaskDefinitionKey())) {
                //6.1 子流程内部驳回
                Execution executionTask = runtimeService.createExecutionQuery().executionId(taskEntity.getExecutionId()).singleResult();
                String parentId = executionTask.getParentId();
                List<Execution> executions = runtimeService.createExecutionQuery().parentId(parentId).list();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                this.moveExecutionsToSingleActivityId(executionIds, backTaskVo.getDistFlowElementId());
            } else {
                //6.2 普通驳回
                List<Execution> executions = runtimeService.createExecutionQuery().parentId(taskEntity.getProcessInstanceId()).list();
                executions.forEach(execution -> executionIds.add(execution.getId()));
                this.moveExecutionsToSingleActivityId(executionIds, backTaskVo.getDistFlowElementId());
            }
        } else {
            throw new RuntimeException("不存在任务实例,请确认!");
        }

    }

    @Override
    public List<FlowNodeVo> getBackNodesByProcessInstanceId(String processInstanceId, String taskId) {
        List<FlowNodeVo> backNods = new ArrayList<>();
        TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(taskId).singleResult();
        String currActId = taskEntity.getTaskDefinitionKey();
        //获取运行节点表中usertask
        String sql = "select t.* from act_ru_actinst t where t.ACT_TYPE_ = 'userTask' " +
                " and t.PROC_INST_ID_=#{processInstanceId} and t.END_TIME_ is not null ";
        List<ActivityInstance> activityInstances = runtimeService.createNativeActivityInstanceQuery().sql(sql)
                .parameter("processInstanceId", processInstanceId)
                .list();
        //获取运行节点表的parallelGateway节点并出重
        sql = "SELECT t.ID_, t.REV_,t.PROC_DEF_ID_,t.PROC_INST_ID_,t.EXECUTION_ID_,t.ACT_ID_, t.TASK_ID_, t.CALL_PROC_INST_ID_, t.ACT_NAME_, t.ACT_TYPE_, " +
                " t.ASSIGNEE_, t.START_TIME_, max(t.END_TIME_) as END_TIME_, t.DURATION_, t.DELETE_REASON_, t.TENANT_ID_" +
                " FROM  act_ru_actinst t WHERE t.ACT_TYPE_ = 'parallelGateway' AND t.PROC_INST_ID_ = #{processInstanceId} and t.END_TIME_ is not null" +
                " and t.ACT_ID_ <> #{actId} GROUP BY t.act_id_";
        List<ActivityInstance> parallelGatewaies = runtimeService.createNativeActivityInstanceQuery().sql(sql)
                .parameter("processInstanceId", processInstanceId)
                .parameter("actId", currActId)
                .list();
        //排序
        if (!CollectionUtils.isEmpty(parallelGatewaies)) {
            activityInstances.addAll(parallelGatewaies);
            activityInstances.sort(Comparator.comparing(ActivityInstance::getEndTime));
        }
        //分组节点
        int count = 0;
        Map<ActivityInstance, List<ActivityInstance>> parallelGatewayUserTasks = new HashMap<>();
        List<ActivityInstance> userTasks = new ArrayList<>();
        ActivityInstance currActivityInstance = null;
        for (ActivityInstance activityInstance : activityInstances) {
            if (BpmnXMLConstants.ELEMENT_GATEWAY_PARALLEL.equals(activityInstance.getActivityType())) {
                count++;
                if (count % 2 != 0) {
                    List<ActivityInstance> datas = new ArrayList<>();
                    currActivityInstance = activityInstance;
                    parallelGatewayUserTasks.put(currActivityInstance, datas);
                }
            }
            if (BpmnXMLConstants.ELEMENT_TASK_USER.equals(activityInstance.getActivityType())) {
                if (count % 2 == 0) {
                    userTasks.add(activityInstance);
                } else {
                    if (parallelGatewayUserTasks.containsKey(currActivityInstance)) {
                        parallelGatewayUserTasks.get(currActivityInstance).add(activityInstance);
                    }
                }
            }
        }
        //组装人员名称
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId).finished().list();
        Map<String, List<HistoricTaskInstance>> taskInstanceMap = new HashMap<>();
        List<String> userCodes = new ArrayList<>();
        historicTaskInstances.forEach(historicTaskInstance -> {
            userCodes.add(historicTaskInstance.getAssignee());
            String taskDefinitionKey = historicTaskInstance.getTaskDefinitionKey();
            if (taskInstanceMap.containsKey(historicTaskInstance.getTaskDefinitionKey())) {
                taskInstanceMap.get(taskDefinitionKey).add(historicTaskInstance);
            } else {
                List<HistoricTaskInstance> tasks = new ArrayList<>();
                tasks.add(historicTaskInstance);
                taskInstanceMap.put(taskDefinitionKey, tasks);
            }
        });
        //组装usertask的数据
        List<User> userList = identityService.createUserQuery().userIds(userCodes).list();
        Map<String, String> activityIdUserNames = this.getApplyers(processInstanceId, userList, taskInstanceMap);
        if (!CollectionUtils.isEmpty(userTasks)) {
            userTasks.forEach(activityInstance -> {
                FlowNodeVo node = new FlowNodeVo();
                node.setNodeId(activityInstance.getActivityId());
                node.setNodeName(activityInstance.getActivityName());
                node.setEndTime(activityInstance.getEndTime());
                node.setUserName(activityIdUserNames.get(activityInstance.getActivityId()));
                backNods.add(node);
            });
        }
        //组装会签节点数据
        if (!CollectionUtils.isEmpty(taskInstanceMap)) {
            parallelGatewayUserTasks.forEach((activity, activities) -> {
                FlowNodeVo node = new FlowNodeVo();
                node.setNodeId(activity.getActivityId());
                node.setEndTime(activity.getEndTime());
                StringBuffer nodeNames = new StringBuffer("会签:");
                StringBuffer userNames = new StringBuffer("审批人员:");
                if (!CollectionUtils.isEmpty(activities)) {
                    activities.forEach(activityInstance -> {
                        nodeNames.append(activityInstance.getActivityName()).append(",");
                        userNames.append(activityIdUserNames.get(activityInstance.getActivityId())).append(",");
                    });
                    node.setNodeName(nodeNames.toString());
                    node.setUserName(userNames.toString());
                    backNods.add(node);
                }
            });
        }
        //去重合并
        List<FlowNodeVo> datas = backNods.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() ->
                        new TreeSet<>(Comparator.comparing(nodeVo -> nodeVo.getNodeId()))), ArrayList::new));

        //排序
        datas.sort(Comparator.comparing(FlowNodeVo::getEndTime));
        return datas;
    }

    private Map<String, String> getApplyers(String processInstanceId, List<User> userList, Map<String, List<HistoricTaskInstance>> taskInstanceMap) {
        Map<String, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, user -> user));
        Map<String, String> applyMap = new HashMap<>();
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        taskInstanceMap.forEach((activityId, taskInstances) -> {
            StringBuffer applyers = new StringBuffer();
            StringBuffer finalApplyers = applyers;
            taskInstances.forEach(taskInstance -> {
                if (!taskInstance.getName().equals(FlowConstant.FLOW_SUBMITTER)) {
                    User user = userMap.get(taskInstance.getAssignee());
                    if (user != null) {
                        if (StringUtils.indexOf(finalApplyers.toString(), user.getDisplayName()) == -1) {
                            finalApplyers.append(user.getDisplayName()).append(",");
                        }
                    }
                } else {
                    String startUserId = processInstance.getStartUserId();
                    User user = identityService.createUserQuery().userId(startUserId).singleResult();
                    if (user != null) {
                        finalApplyers.append(user.getDisplayName()).append(",");
                    }
                }
            });
            if (applyers.length() > 0) {
                applyers = applyers.deleteCharAt(applyers.length() - 1);
            }
            applyMap.put(activityId, applyers.toString());
        });
        return applyMap;
    }

    @Override
    public String beforeAddSignTask(AddSignTaskVo addSignTaskVo) {
        return this.addSignTask(addSignTaskVo, false);
    }

    @Override
    public String afterAddSignTask(AddSignTaskVo addSignTaskVo) {
        return this.addSignTask(addSignTaskVo, true);
    }

    @Override
    public String addSignTask(AddSignTaskVo addSignTaskVo, Boolean flag) {
        TaskEntityImpl taskEntity = (TaskEntityImpl) taskService.createTaskQuery().taskId(addSignTaskVo.getTaskId()).singleResult();
        //1.把当前的节点设置为空
        if (taskEntity != null) {
            //如果是加签再加签
            String parentTaskId = taskEntity.getParentTaskId();
            if (StringUtils.isBlank(parentTaskId)) {
                taskEntity.setOwner(addSignTaskVo.getUserCode());
                taskEntity.setAssignee(null);
                taskEntity.setCountEnabled(true);
                if (flag) {
                    taskEntity.setScopeType(FlowConstant.AFTER_ADDSIGN);
                } else {
                    taskEntity.setScopeType(FlowConstant.BEFORE_ADDSIGN);
                }
                //1.2 设置任务为空执行者
                taskService.saveTask(taskEntity);
            }
            //2.添加加签数据
            this.createSignSubTasks(addSignTaskVo, taskEntity);
            //3.添加审批意见
            String type = flag ? CommentEnum.HJQ.toString() : CommentEnum.QJQ.toString();
            this.addComment(addSignTaskVo.getTaskId(), addSignTaskVo.getUserCode(), addSignTaskVo.getProcessInstanceId(),
                    type, addSignTaskVo.getMessage());
            return flag ? "后加签成功" : "前加签成功";
        } else {
            throw new RuntimeException("不存在任务实例，请确认!");
        }

    }

    /**
     * 创建加签子任务
     *
     * @param signVo     加签参数
     * @param taskEntity 父任务
     */
    private void createSignSubTasks(AddSignTaskVo signVo, TaskEntity taskEntity) {
        if (!CollectionUtils.isEmpty(signVo.getSignPersoneds())) {
            String parentTaskId = taskEntity.getParentTaskId();
            if (StringUtils.isBlank(parentTaskId)) {
                parentTaskId = taskEntity.getId();
            }
            String finalParentTaskId = parentTaskId;
            //1.创建被加签人的任务列表
            signVo.getSignPersoneds().forEach(userCode -> {
                if (StringUtils.isNotBlank(userCode)) {
                    this.createSubTask(taskEntity, finalParentTaskId, userCode);
                }
            });
            String taskId = taskEntity.getId();
            if (StringUtils.isBlank(taskEntity.getParentTaskId())) {
                //2.创建加签人的任务并执行完毕
                Task task = this.createSubTask(taskEntity, finalParentTaskId, signVo.getUserCode());
                taskId = task.getId();
            }
            Task taskInfo = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (null != taskInfo) {
                taskService.complete(taskId);
            }
            //如果是候选人，需要删除运行时候选表种的数据。
            long candidateCount = taskService.createTaskQuery().taskId(parentTaskId).taskCandidateUser(signVo.getUserCode()).count();
            if (candidateCount > 0) {
                taskService.deleteCandidateUser(parentTaskId, signVo.getUserCode());
            }
        }
    }

    @Override
    public String unClaimTask(ClaimTaskVo claimTaskVo) {
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(claimTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(claimTaskVo.getTaskId(), claimTaskVo.getProcessInstanceId(), CommentEnum.QS.toString(), claimTaskVo.getMessage());
            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(claimTaskVo.getTaskId());
            boolean flag = false;
            if (!CollectionUtils.isEmpty(identityLinks)) {
                for (IdentityLink link : identityLinks) {
                    if (IdentityLinkType.CANDIDATE.equals(link.getType())) {
                        flag = true;
                        break;
                    }
                }
            }
            //2.反签收
            if (flag) {
                taskService.claim(claimTaskVo.getTaskId(), null);
                return "反签收成功";
            } else {
                throw new RuntimeException("由于没有候选人或候选组,会导致任务无法认领,请确认.");
            }
        } else {
            throw new RuntimeException("反签收失败");
        }
    }

    @Override
    public String claimTask(ClaimTaskVo claimTaskVo) {
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(claimTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(claimTaskVo.getTaskId(), claimTaskVo.getProcessInstanceId(), CommentEnum.QS.toString(), claimTaskVo.getMessage());
            //2.签收
            taskService.claim(claimTaskVo.getTaskId(), claimTaskVo.getUserCode());
            return "签收成功";
        } else {
            throw new RuntimeException("签收失败");
        }
    }

    @Override
    public String delegateTask(DelegateTaskVo delegateTaskVo) {
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(delegateTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.添加审批意见
            this.addComment(delegateTaskVo.getTaskId(), delegateTaskVo.getUserCode(), delegateTaskVo.getProcessInstanceId(), CommentEnum.WP.toString(), delegateTaskVo.getMessage());
            //2.设置审批人就是当前登录人
            taskService.setAssignee(delegateTaskVo.getTaskId(), delegateTaskVo.getUserCode());
            //3.执行委派
            taskService.delegateTask(delegateTaskVo.getTaskId(), delegateTaskVo.getDelegateUserCode());
            return "委派成功";
        } else {
            throw new RuntimeException("没有运行时的任务实例,请确认!");
        }
    }

    @Override
    public String turnTask(TurnTaskVo turnTaskVo) {
        TaskEntityImpl currTask = (TaskEntityImpl) taskService.createTaskQuery().taskId(turnTaskVo.getTaskId()).singleResult();
        if (currTask != null) {
            //1.生成历史记录
            TaskEntity task = this.createSubTask(currTask, turnTaskVo.getUserCode());
            //2.添加审批意见
            this.addComment(task.getId(), turnTaskVo.getUserCode(), turnTaskVo.getProcessInstanceId(), CommentEnum.ZB.toString(), turnTaskVo.getMessage());
            taskService.complete(task.getId());
            //3.转办
            taskService.setAssignee(turnTaskVo.getTaskId(), turnTaskVo.getTurnToUserId());
            taskService.setOwner(turnTaskVo.getTaskId(), turnTaskVo.getUserCode());
            return "转办成功";
        } else {
            throw new RuntimeException("没有运行时的任务实例,请确认!");
        }
    }

    @Override
    public String complete(CompleteTaskVo params) {
        if (StrUtil.allNotEmpty(params.getProcessInstanceId(), params.getTaskId())) {
            //1.查看当前任务是存在
            TaskEntity taskEntity = (TaskEntity) taskService.createTaskQuery().taskId(params.getTaskId()).singleResult();
            if (taskEntity != null) {
                String taskId = params.getTaskId();
                //2.委派处理
                if (DelegationState.PENDING.equals(taskEntity.getDelegationState())) {
                    //2.1生成历史记录
                    TaskEntity task = this.createSubTask(taskEntity, params.getUserCode());
                    taskService.complete(task.getId());
                    taskId = task.getId();
                    //2.2执行委派
                    taskService.resolveTask(params.getTaskId(), params.getVariables());
                } else {
                    //3.1修改执行人 其实我这里就相当于签收了
                    taskService.setAssignee(params.getTaskId(), params.getUserCode());
                    //3.2执行任务
                    taskService.complete(params.getTaskId(), params.getVariables());
                    //4.处理加签父任务
                    String parentTaskId = taskEntity.getParentTaskId();
                    if (StringUtils.isNotBlank(parentTaskId)) {
                        String tableName = managementService.getTableName(TaskEntity.class);
                        String sql = "select count(1) from " + tableName + " where PARENT_TASK_ID_=#{parentTaskId}";
                        long subTaskCount = taskService.createNativeTaskQuery().sql(sql).parameter("parentTaskId", parentTaskId).count();
                        if (subTaskCount == 0) {
                            Task task = taskService.createTaskQuery().taskId(parentTaskId).singleResult();
                            //处理前后加签的任务
                            taskService.resolveTask(parentTaskId);
                            if (FlowConstant.AFTER_ADDSIGN.equals(task.getScopeType())) {
                                taskService.complete(parentTaskId);
                            }
                        }
                    }
                }
                String type = params.getType() == null ? CommentEnum.SP.toString() : params.getType();
                //5.生成审批意见
                this.addComment(taskId, params.getUserCode(), params.getProcessInstanceId(), type, params.getMessage());
            } else {
                throw new RuntimeException("没有此任务，请确认!");
            }
        } else {
            throw new RuntimeException("请输入正确的参数!");
        }

        throw new RuntimeException("请输入正确的参数!");
    }

    @Override
    public Task findTaskById(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    @Override
    public Page<TaskVo> getApplyingTasks(TaskQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        return flowableTaskDao.getApplyingTasks(params);
    }

    @Override
    public Page<TaskVo> getApplyedTasks(TaskQueryVo params, Query query) {
        PageHelper.startPage(query.getPageNum(), query.getPageSize());
        return flowableTaskDao.getApplyedTasks(params);
    }

    @Override
    public List<User> getApprovers(String processInstanceId) {
        List<User> users = new ArrayList<>();
        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(task -> {
                if (StringUtils.isNotBlank(task.getAssignee())) {
                    //1.审批人ASSIGNEE_是用户id
                    User user = identityService.createUserQuery().userId(task.getAssignee()).singleResult();
                    if (user != null) {
                        users.add(user);
                    }
                    //2.审批人ASSIGNEE_是组id
                    List<User> gusers = identityService.createUserQuery().memberOfGroup(task.getAssignee()).list();
                    if (!CollectionUtils.isEmpty(gusers)) {
                        users.addAll(gusers);
                    }
                } else {
                    List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
                    if (!CollectionUtils.isEmpty(identityLinks)) {
                        identityLinks.forEach(identityLink -> {
                            //3.审批人ASSIGNEE_为空,用户id
                            if (StringUtils.isNotBlank(identityLink.getUserId())) {
                                User user = identityService.createUserQuery().userId(identityLink.getUserId()).singleResult();
                                if (user != null) {
                                    users.add(user);
                                }
                            } else {
                                //4.审批人ASSIGNEE_为空,组id
                                List<User> gusers = identityService.createUserQuery().memberOfGroup(identityLink.getGroupId()).list();
                                if (!CollectionUtils.isEmpty(gusers)) {
                                    users.addAll(gusers);
                                }
                            }
                        });
                    }
                }
            });
        }
        return users;
    }
}
