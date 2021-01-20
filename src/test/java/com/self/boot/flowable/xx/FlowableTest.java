package com.self.boot.flowable.xx;

import com.self.boot.dto.flowable.DeploymentDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 已经支持加签、动态增加实例中的节点、支持cmmn、dmn规范
 *
 *
 *
 * @author: jinyun
 * @date: 2021/1/15
 */
@Slf4j
@SpringBootTest
public class FlowableTest {

    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private TaskService taskService;


    @Test
    public void createTableTest() {
        //时间格式依然为ISO 8601格式，一年两个月三天四小时五分六秒内，可以写成P1Y2M3DT4H5M6S，P是开始标记，
        // T是时间和日期分割标记，没有日期只有时间T是不能省去的，比如1小时执行一次应该写成PT1H。
    }


    /**
     * 将自定义的流程部署到数据库 act_re_deployment  可以动态部署 每次部署会生成部署记录
     * <p>
     * 一次部署同时产生对应的流程定义记录  act_re_procdef 保存就是原生的xml数据信息 每次都会生成流程定义记录  但是会有版本号来约束
     * once starting process instance, merely use the latest version.
     * 标识我们定义的xml流程语言的就是 procdef 的key  [key.bpmn.xml]
     *
     *
     * springboot 默认在classpath/processes/***.bpmn 文件只要不发布就自动运行
     */
    @Test
    public void deployXMLTest() {
        // mock  DeploymentEntity[id=15ed9f99-56f6-11eb-baef-3c7c3fd3a48f, name=test1]
        DeploymentDTO deploymentDTO = new DeploymentDTO("test3", "第五次部署", FlowableTestConst.TENANT_ID);

        Deployment deploy = repositoryService.createDeployment()
                // classloader scans pkg. [/classes/**]
                .addClasspathResource("\\diagram\\test.bpmn20.xml")
                .key(deploymentDTO.getDeploymentKey())
                .name(deploymentDTO.getDeploymentName())
                .tenantId(deploymentDTO.getTenantId())
                .deploy();
        System.out.println(deploy.toString());
        log.info("deployment id: {}", deploy.toString());
        // DeploymentEntity[id=7913e31f-56f3-11eb-a316-3c7c3fd3a48f, name=null]

//        DeploymentEntity[id=91d6b66f-5706-11eb-993e-3c7c3fd3a48f, name=第四次部署]
        //DeploymentEntity[id=9fb7f546-5708-11eb-a214-3c7c3fd3a48f, name=第五次部署]
    }

    /**
     * 查询相关的api
     * <p>
     * 查询流程部署定义
     */
    @Test
    public void queryTest() {
        // wrapper deployment query
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentId("15ed9f99-56f6-11eb-baef-3c7c3fd3a48f").singleResult();
        System.out.println(deployment);
    }

    /**
     * 开启流程 按照流程定义的key  直接开启流程：没有定义监听器报错  写了一个执行监听器, debug解决 ReflectUtil.loadClass()
     * 使用了 最新版本的流程定义的key  业务key  自定义的参数预留  租户id
     * 表变化：
     * act_ru_actinst: 用来存储运行时的节点
     *
     * 下面两个表一起控制了用户任务的执行开始
     * act_ru_execution: ---- 执行实例表 ---   如果是一条直线  那么只有一条记录在这 如果有偏向网关  ===>>>  流程实例：执行实例 == 1：n
     *  is_active: 1  |||   执|行完毕就设置成0  所有都存入历史表
     *
     * 如果是线性的工作流 ==>>> executionId 和 proc_inst_id 是一样的
     * 如果是树形结构 ==>>>
     *
     *
     * act_ru_task:
     *
     *
     * variables: act_ru_variable        id = instant id = execution id
     *
     */
    @Test
    public void startProcessInstantTest() {
        Map<String, Object> variables = new HashMap<>(1 << 2);
        variables.put("employee", "jack");
        variables.put("nrOfHolidays", 3);
        variables.put("description", "回家看看");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKeyAndTenantId(FlowableTestConst.PROC_DEF_KEY_TEST,
                FlowableTestConst.BUSINESS_KEY,
                variables, FlowableTestConst.TENANT_ID);
        System.out.println(processInstance);
        // ProcessInstance[b0775f12-5708-11eb-996e-3c7c3fd3a48f]
        // 并且回调了我们定义的TestListener.
        // 开启流程到了测试人员测试这个环境 一开起流程就回调方法 然后我们设置在测试人员测试这个节点上的监听器start就触发了。
    }

    /**
     * 查询方法
     */
    @Test
    public void queryVariableTest() {
        List<String> ids = runtimeService.getActiveActivityIds("b0775f12-5708-11eb-996e-3c7c3fd3a48f");
        ids.forEach(System.out::println);
        // sid-D73878D3-A611-4643-B8EC-DE65048F61FD
    }


    @Test
    public void taskServiceTest() {
//        taskService.setAssignee();
    }

}
