package org.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

/**
 * When the execution arrives at the service task,
 * the class that is referenced in the BPMN 2.0 XML is instantiated and called.
 *
 * @author: jinyun
 * @date: 2021/1/21
 */
public class CallExternalSystemDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        System.out.println("Calling the external system for employee "
                + execution.getVariable("employee"));
    }
}
