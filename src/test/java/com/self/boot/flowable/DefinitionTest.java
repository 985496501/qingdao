package com.self.boot.flowable;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.SuspendState;
import com.self.boot.common.flowable.service.IFlowableProcessDefinitionService;
import com.self.boot.common.flowable.vo.ProcessDefinitionQueryVo;
import com.self.boot.common.flowable.vo.ProcessDefinitionVo;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 流程定义的测试
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Slf4j
@SpringBootTest
public class DefinitionTest {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IFlowableProcessDefinitionService definitionService;

    /**
     * 将自定义的流程部署到数据库 act_re_deployment  可以动态部署 每次部署会生成部署记录
     * <p>
     * 一次部署同时产生对应的流程定义记录  act_re_procdef 保存就是原生的xml数据信息 每次都会生成流程定义记录  但是会有版本号来约束
     * once starting process instance, merely use the latest version.
     * 标识我们定义的xml流程语言的就是 procdef 的key  [key.bpmn.xml]
     * <p>
     * <p>
     * springboot 默认在classpath/processes/***.bpmn 文件只要不发布就自动运行
     *
     * key: 这个值特别重要 目前开发确认流程 通过流程常量开启
     */
    @Test
    public void deployTest() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("holiday.bpmn20.xml")
                .name("请假")
                .key("holiday")
                .tenantId("01")
                .category("没有策略")
                .deploy();
        log.info("\n =======================================>> \n id={}, time={}, name={}, category={}, key={}, version={}",
                deployment.getId(), deployment.getDeploymentTime(), deployment.getName(),
                deployment.getCategory(), deployment.getKey(), deployment.getEngineVersion());
        // id=d5ba3a0a-5af9-11eb-8d82-3c7c3fd3a48f, time=Wed Jan 20 16:31:04 CST 2021, name=测试1, category=没有策略, key=test1, version=null
    }


    /**
     * 查询定义语句的分页列表
     */
    @Test
    public void getPagerModelTest() {
        ProcessDefinitionQueryVo queryVo = ProcessDefinitionQueryVo.builder().modelKey("test").build();
        Page<ProcessDefinitionVo> pagerModel = definitionService.getPagerModel(queryVo, new Query());
        pagerModel.getResult().forEach(System.out::println);
    }

    /**
     * 根据id查询详情
     */
    @Test
    public void getByIdTest() {
        ProcessDefinitionVo definitionVo = definitionService.getById("test:3:d5e05fad-5af9-11eb-8d82-3c7c3fd3a48f");
        System.out.println(definitionVo.toString());
    }

    @Test
    public void suspendOrActivateProcessDefinitionByIdTest() {
        definitionService.suspendOrActivateProcessDefinitionById("test:3:d5e05fad-5af9-11eb-8d82-3c7c3fd3a48f", SuspendState.ACTIVE);
    }




}
