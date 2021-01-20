package com.self.boot.common.flowable.conf;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * 生成流程图片
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Component
public class FlowProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
    private static final String IMAGE_TYPE = "png";

    @Value("宋体")
    private String activityFontName;
    @Value("宋体")
    private String labelFontName;
    @Value("宋体")
    private String annotationFontName;

    /**
     * 生成图片流
     *
     * @param bpmnModel             模型
     * @param highLightedActivities 活动节点
     * @param highLightedFlows      高亮线
     * @return
     */
    public InputStream generateDiagram(BpmnModel bpmnModel, List<String> highLightedActivities, List<String> highLightedFlows) {
        return generateDiagram(bpmnModel, IMAGE_TYPE, highLightedActivities,
                highLightedFlows, activityFontName, labelFontName, annotationFontName,
                null, 1.0, true);
    }
}
