package com.self.boot.common.flowable.cmd;

import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;

/**
 * 创建子流程
 * @author jinyun
 */
public class AddChildExecutionCmd implements Command<String> {

    private final ExecutionEntity pexecution;

    public AddChildExecutionCmd(ExecutionEntity pexecution) {
        this.pexecution = pexecution;
    }

    @Override
    public String execute(CommandContext commandContext) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil.getExecutionEntityManager(commandContext);
        ExecutionEntity childExecution = executionEntityManager.createChildExecution(pexecution);
        return childExecution.getId();
    }
}
