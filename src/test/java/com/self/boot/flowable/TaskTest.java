package com.self.boot.flowable;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.core.CommentEnum;
import com.self.boot.common.flowable.core.Query;
import com.self.boot.common.flowable.core.variable.HolidayProcessVariable;
import com.self.boot.common.flowable.service.IFlowableTaskService;
import com.self.boot.common.flowable.vo.CompleteTaskVo;
import com.self.boot.common.flowable.vo.TaskQueryVo;
import com.self.boot.common.flowable.vo.TaskVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * 任务测试
 *
 * @author: jinyun
 * @date: 2021/1/21
 */
@SpringBootTest
public class TaskTest {
    @Autowired
    private IFlowableTaskService taskService;

    /**
     * 查询指定用户的代办任务列表
     */
    @Test
    public void getApplyingTasksTest() {
        TaskQueryVo taskQueryVo = TaskQueryVo.builder().userCode("0000").build();
        Page<TaskVo> tasks = taskService.getApplyingTasks(taskQueryVo, new Query());
        tasks.getResult().forEach(System.out::println);
        // 手动改了一个 act_ru_task 的 consignee 值查询出来
        // TaskVo(taskId=44e6f8bd-5b9e-11eb-a2a1-3c7c3fd3a48f, taskName=填写请假单, approver=null, approverId=null, formName=请假申请,
        // businessKey=2a2a03e44aa3240fd64dccc595543699, processInstanceId=44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f,
        // startTime=Thu Jan 21 12:08:08 CST 2021, endTime=null, systemSn=01)
    }


    /**
     * 完成任务
     */
    @Test
    public void completeTest() {
        CompleteTaskVo completeTaskVo = new CompleteTaskVo();
        // 95542d6f-5bb2-11eb-91c4-3c7c3fd3a48f
        completeTaskVo.setTaskId("95542d6f-5bb2-11eb-91c4-3c7c3fd3a48f");
        completeTaskVo.setUserCode("0000");

        HashMap<String, Object> map = new HashMap<>(2);
        map.put("days", 6);
        map.put(HolidayProcessVariable.TEAM_LEADER, "1001");
        completeTaskVo.setVariables(map);
        completeTaskVo.setProcessInstanceId("44e3c464-5b9e-11eb-a2a1-3c7c3fd3a48f");
        completeTaskVo.setMessage("0000号申请假期");
        completeTaskVo.setType(CommentEnum.APPLY.getName());
        taskService.complete(completeTaskVo);
    }


}
