package com.self.boot.common.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

/**
 * @author: jinyun
 * @date: 2021/1/15
 */
@Component
public class TestExecutionListener implements ExecutionListener {
    @Override
    public void notify(DelegateExecution execution) {
        System.out.println("==================================================================");
        String processDefinitionId = execution.getProcessDefinitionId();
        String eventName = execution.getEventName();
        String businessKey = execution.getProcessInstanceBusinessKey();
        String tenantId = execution.getTenantId();
        String id = execution.getId();
        String currentActivityId = execution.getCurrentActivityId();
        System.out.println("processDefinitionId  :" + processDefinitionId);
        System.out.println("eventName  :" + eventName);
        System.out.println("businessKey  :" + businessKey);
        System.out.println("tenantId  :" + tenantId);
        System.out.println("id  :" + id);
        System.out.println("currentActivityId  :" + currentActivityId);
    }
}
