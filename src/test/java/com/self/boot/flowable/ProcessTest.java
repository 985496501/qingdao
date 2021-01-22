package com.self.boot.flowable;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.ProcessDefinitionKey;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.SuspendState;
import com.self.boot.common.flowable.core.variable.HolidayProcessVariable;
import com.self.boot.common.flowable.service.IFlowableProcessInstanceService;
import com.self.boot.common.flowable.service.IFlowableTaskService;
import com.self.boot.common.flowable.vo.CompleteTaskVo;
import com.self.boot.common.flowable.vo.ProcessInstanceQueryVo;
import com.self.boot.common.flowable.vo.ProcessInstanceVo;
import com.self.boot.common.flowable.vo.StartProcessInstanceVo;
import com.self.boot.common.util.UuidUtils;
import org.flowable.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: jinyun
 * @date: 2021/1/20
 */
@SpringBootTest
public class ProcessTest {
    @Autowired
    private IFlowableProcessInstanceService instanceService;

    @Autowired
    private IFlowableTaskService taskService;

    /**
     * 当前登录人id
     */
    private static final String CURRENT_USER = "00000";

    /**
     * 系统标识 可以支持多系统工作流
     * SYSTEM_SN == TENANT_ID
     */
    private static final String SYSTEM_SN = "01";

    /**
     * 开启流程实例
     *
     */
    @Test
    public void startProcessInstanceByKeyTest() {
        Map<String, Object> map = new HashMap<>(4);
        map.put(HolidayProcessVariable.DAYS, 5);
        map.put(HolidayProcessVariable.MANAGER, "0002");
        map.put(HolidayProcessVariable.DIRECTOR, "0001");

        StartProcessInstanceVo startProcessInstanceVo = StartProcessInstanceVo.builder()
                // 业务系统表单的id 和 业务系统作交互就必须传入这个值
                .businessKey(UuidUtils.getUUID())
                .processDefinitionKey(ProcessDefinitionKey.HOLIDAY)
                // 这个非常重要 这个是整个实例的全局业务变量参数 定义bpmn的时候指定预留的变量值 在此填充
                .formName("请假申请")
                .variables(map)
                .creator(CURRENT_USER)
                .currentUserCode(CURRENT_USER)
                .systemSn(SYSTEM_SN)
                .build();
        ProcessInstance processInstance = instanceService.startProcessInstanceByKey(startProcessInstanceVo);
        //ProcessInstance[9e65f5d1-5b9c-11eb-b265-3c7c3fd3a48f]
        //ProcessInstance[44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f]
        System.out.println(processInstance);
    }

    /**
     * 分页查询流程实例的列表
     */
    @Test
    public void getPagerModelTest() {
        ProcessInstanceQueryVo queryVo = ProcessInstanceQueryVo.builder()
                .formName("")
                .userCode("")
                .userName("")
                .build();
        Page<ProcessInstanceVo> pagerModel = instanceService.getPagerModel(queryVo, new Query());
        pagerModel.getResult().forEach(System.out::println);
        //ProcessInstanceVo(processInstanceId=44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f, processDefinitionId=holiday:1:3f0c7a2f-5b9c-11eb-accb-3c7c3fd3a48f, suspensionState=1, formName=请假申请, businessKey=2a2a03e44aa3240fd64dccc595543699, startTime=Thu Jan 21 12:08:08 CST 2021, endTime=null, approver=null, starter=null, starterId=null, systemSn=01)
        //ProcessInstanceVo(processInstanceId=9e65f5d1-5b9c-11eb-b265-3c7c3fd3a48f, processDefinitionId=holiday:1:3f0c7a2f-5b9c-11eb-accb-3c7c3fd3a48f, suspensionState=1, formName=请假申请, businessKey=a80aec6733ad3407f55741c769e19522, startTime=Thu Jan 21 11:56:19 CST 2021, endTime=null, approver=null, starter=null, starterId=null, systemSn=01)
    }

    /**
     * 查询我发起的流程实例
     */
    @Test
    public void getMyProcessInstancesTest() {
        ProcessInstanceQueryVo queryVo = ProcessInstanceQueryVo.builder()
                .formName("")
                .userCode(CURRENT_USER)
                .userName("")
                .build();
        Page<ProcessInstanceVo> pagerModel = instanceService.getMyProcessInstances(queryVo, new Query());
        pagerModel.getResult().forEach(System.out::println);
        // ProcessInstanceVo(processInstanceId=44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f, processDefinitionId=holiday:1:3f0c7a2f-5b9c-11eb-accb-3c7c3fd3a48f, suspensionState=1, formName=请假申请, businessKey=2a2a03e44aa3240fd64dccc595543699, startTime=Thu Jan 21 12:08:08 CST 2021, endTime=null, approver=null, starter=null, starterId=null, systemSn=01)
        //ProcessInstanceVo(processInstanceId=9e65f5d1-5b9c-11eb-b265-3c7c3fd3a48f, processDefinitionId=holiday:1:3f0c7a2f-5b9c-11eb-accb-3c7c3fd3a48f, suspensionState=1, formName=请假申请, businessKey=a80aec6733ad3407f55741c769e19522, startTime=Thu Jan 21 11:56:19 CST 2021, endTime=null, approver=null, starter=null, starterId=null, systemSn=01)
    }

    /**
     * 获取流程图片
     */
    @Test
    public void createImageTest() throws IOException {
        byte[] imgBytes = instanceService.createImage("99a61dc2-5bb9-11eb-83ea-3c7c3fd3a48f");
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("E:\\file\\10.jpg"));
        imageOutput.write(imgBytes, 0, imgBytes.length);
        imageOutput.close();
    }

    /**
     * 实例状态
     */
    @Test
    public void suspendOrActivateProcessInstanceByIdTest() {
        instanceService.suspendOrActivateProcessInstanceById("44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f", SuspendState.ACTIVE);
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTest() {
        CompleteTaskVo completeTaskVo = new CompleteTaskVo();
//        completeTaskVo.setTaskId();
//        taskService.complete();
    }
}
