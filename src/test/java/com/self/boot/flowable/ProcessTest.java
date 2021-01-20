package com.self.boot.flowable;

import com.self.boot.common.flowable.core.ProcessDefinitionKey;
import com.self.boot.common.flowable.service.IFlowableProcessInstanceService;
import com.self.boot.common.flowable.vo.StartProcessInstanceVo;
import com.self.boot.common.util.UuidUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

/**
 * @author: jinyun
 * @date: 2021/1/20
 */
@SpringBootTest
public class ProcessTest {
    @Autowired
    private IFlowableProcessInstanceService instanceService;

    @Test
    public void startProcessInstanceByKeyTest() {
        StartProcessInstanceVo startProcessInstanceVo = StartProcessInstanceVo.builder()
                // 业务系统表单的id 和 业务系统作交互就必须传入这个值
                .businessKey(UuidUtils.getUUID())
                .processDefinitionKey(ProcessDefinitionKey.TEST)
                // 这个非常重要 这个是整个实例的全局业务变量参数 定义bpmn的时候指定预留的变量值 在此填充
                .variables(new HashMap<>())
                .build();

    }


}
