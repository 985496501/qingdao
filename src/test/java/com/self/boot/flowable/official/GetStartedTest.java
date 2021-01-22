package com.self.boot.flowable.official;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.*;

/**
 * 流程定义的测试
 * <p>
 * 工作流的核心就是 某人的任务为出发点
 * <p>
 * 一般就是一个人的代办任务  已办任务
 * 查询代办任务 执行代办任务 推给下一个人
 * 下一个人再查询给自己的任务 再次完成 再次推给下一个人
 * 重复
 * 直到任务结束
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Slf4j
@SpringBootTest
public class GetStartedTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;


    /**
     * process id
     */
    private static final String PROCESS_ID = "62e07ded-5c84-11eb-a31c-3c7c3fd3a48f";

    /**
     * process id  bpmn id
     */
    private static final String DEFINITION_KEY = "holidayRequest";

    /**
     * 提交表单业务id
     */
    private static final String BUSINESS_KEY = "456123786132";


    /**
     * 将自定义的流程部署到数据库 act_re_deployment  可以动态部署 每次部署会生成部署记录
     * <p>
     * 一次部署同时产生对应的流程定义记录  act_re_procdef 保存就是原生的xml数据信息 每次都会生成流程定义记录  但是会有版本号来约束
     * once starting process instance, merely use the latest version.
     * 标识我们定义的xml流程语言的就是 procdef 的key  [key.bpmn.xml]
     * <p>
     * <p>
     * springboot 默认在classpath/processes/***.bpmn 文件只要不发布就自动运行
     * <p>
     * key: 这个值特别重要 目前开发确认流程 通过流程常量开启
     */
    @Test
    public void deployTest() {
        Deployment deployment = repositoryService.createDeployment()
                // 通过classpath下的 xml png都存到数据库
                .addClasspathResource("holiday-request.bpmn20.xml")
//                .addClasspathResource("holiday-request.bpmn20.png")
                .deploy();
        log.info("\n =======================================>> \n id={}, time={}, name={}, category={}, key={}, version={}",
                deployment.getId(), deployment.getDeploymentTime(), deployment.getName(),
                deployment.getCategory(), deployment.getKey(), deployment.getEngineVersion());
    }


    @Test
    public void queryTest() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId("7f5ab407-5bb9-11eb-9062-3c7c3fd3a48f")
                .singleResult();
        System.out.println("Found process definition : " + processDefinition.getName());
    }


    @Test
    public void startProcessTest() {
        // 模仿一个表单
        HashMap<String, Object> hashMap = new HashMap<>(4);
        hashMap.put("employee", "zhangsan");
        hashMap.put("nrOfHolidays", 3);
        hashMap.put("description", "家里有事");
        // When the process instance is started, an execution is created and put in the start event.
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(DEFINITION_KEY, BUSINESS_KEY, hashMap);
        System.out.println(processInstance.toString());

        //  ProcessInstance[62e07ded-5c84-11eb-a31c-3c7c3fd3a48f]
    }


    // query and complete the task
    // Task[id=f6b9b330-5c7b-11eb-b6ef-3c7c3fd3a48f, name=Approve or reject request]
    @Test
    public void queryListTest() {
        List<Task> managers = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        if (managers != null) {
            managers.forEach(System.out::println);
        }
        // 99a903fa-5bb9-11eb-83ea-3c7c3fd3a48f

        // ==============================================================
        System.out.println();

        List<Task> zhangsan = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        if (zhangsan != null) {
            zhangsan.forEach(System.out::println);
        }

        // Task[id=d02ddb1f-5bbb-11eb-821a-3c7c3fd3a48f, name=Holiday approved]
    }

    /**
     * Using the task identifier, we can now get the specific process instance variables and show on the screen the actual request:
     * <p>
     * nrOfHolidays 3
     * description 家里有事
     * employee zhangsan
     */
    @Test
    public void getVariableTest() {
        Map<String, Object> variables = taskService.getVariables("99a903fa-5bb9-11eb-83ea-3c7c3fd3a48f");
        variables.forEach((x, y) -> {
            System.out.println(x + " " + y);
        });
    }

    /**
     * 完成任务by Manager
     */
    @Test
    public void completeByManagerTest() {
        Task task = taskService.createTaskQuery()
                .taskCandidateGroup("managers")
                .processInstanceBusinessKey(BUSINESS_KEY).singleResult();
        // mimic variables
        System.out.println(task);
        Map<String, Object> variables = Collections.singletonMap("approved", true);
        taskService.complete(task.getId(), variables);
    }


    @Test
    public void completeTest() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                .processInstanceBusinessKey(BUSINESS_KEY).singleResult();
        taskService.complete(task.getId());
    }


    @Test
    public void queryCurrentTaskTest() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                .processInstanceBusinessKey(BUSINESS_KEY).singleResult();
        System.out.println(task);
    }

    @Test
    public void finishByVariablesTest() {
        taskService.complete("9fff8943-5c82-11eb-849f-3c7c3fd3a48f");
    }

    // working with historical data
    // it automatically stores audit data or historical data for all the process instances.


    /**
     * only the activities for one particular process instance
     * only the activities that have finished
     */
    @Test
    public void historyServiceTest() {
        List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(PROCESS_ID)
                .finished()
                .orderByHistoricActivityInstanceEndTime().asc()
                .list();
        if (list != null) {
            list.forEach(System.out::println);
        }
    }


    @Test
    public void revokeTest() {
        Task task = taskService.createTaskQuery()
                .taskAssignee("zhangsan")
                .processInstanceBusinessKey(BUSINESS_KEY)
                .singleResult();
        System.out.println(task);

//        Task task = zhangsan.get(0);
//  Task[id=9fff8943-5c82-11eb-849f-3c7c3fd3a48f, name=Holiday approved]
        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(PROCESS_ID)
                .moveActivityIdTo(task.getTaskDefinitionKey(), "approveTask")
                .changeState();

        // holidayApprovedTask
    }


    @Test
    public void queryCurrTaskTest() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(PROCESS_ID).singleResult();
        Task task = taskService.createTaskQuery()
                .processInstanceBusinessKey(BUSINESS_KEY)
                .processInstanceId(processInstance.getProcessInstanceId())
                .singleResult();
        System.out.println(task);
    }


    @Test
    public void queryHistoryTest() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery().processInstanceId(PROCESS_ID).list();
        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println(historicTaskInstance);
        }
    }


    @Test
    public void selectDiagramTest() {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId(PROCESS_ID).singleResult();


        if (processInstance == null) {
            return;
        }

        // 查询已经完成的 activity instance
        List<HistoricActivityInstance> historicActivityInstances = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(PROCESS_ID)
                .finished()
                .list();
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();

        // 获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        if (CollUtil.isNotEmpty(historicActivityInstances)) {
            for (HistoricActivityInstance instance : historicActivityInstances) {
                String activityType = instance.getActivityType();
                if (activityType.equals("sequenceFlow") || activityType.equals("exclusiveGateway")) {
                    flows.add(instance.getActivityId());
                } else if (activityType.equals("userTask") || activityType.equals("startEvent")) {
                    activityIds.add(instance.getActivityId());
                }

            }
        }

        List<Task> list = taskService.createTaskQuery().processInstanceId(PROCESS_ID).list();
        if (CollUtil.isNotEmpty(list)) {
            for (Task task : list) {
                activityIds.add(task.getTaskDefinitionKey());
            }
        }

        DefaultProcessDiagramGenerator generator = new DefaultProcessDiagramGenerator();
        InputStream png = generator.generateDiagram(bpmnModel, "png", activityIds,
                flows, true);
        assert png != null;
    }

}
