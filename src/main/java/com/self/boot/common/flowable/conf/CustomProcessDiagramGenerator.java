package com.self.boot.common.flowable.conf;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.ProcessDiagramGenerator;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;

/**
 * 自定义图形生成器
 * todo: 自定义图形生成器
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public class CustomProcessDiagramGenerator implements ProcessDiagramGenerator {
    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, List<String> list, List<String> list1, String s1, String s2, String s3, ClassLoader classLoader, double v, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, List<String> list, List<String> list1, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, List<String> list, List<String> list1, double v, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, List<String> list, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, List<String> list, double v, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, String s1, String s2, String s3, ClassLoader classLoader, boolean b) {
        return null;
    }

    @Override
    public InputStream generateDiagram(BpmnModel bpmnModel, String s, String s1, String s2, String s3, ClassLoader classLoader, double v, boolean b) {
        return null;
    }

    @Override
    public InputStream generatePngDiagram(BpmnModel bpmnModel, boolean b) {
        return null;
    }

    @Override
    public InputStream generatePngDiagram(BpmnModel bpmnModel, double v, boolean b) {
        return null;
    }

    @Override
    public InputStream generateJpgDiagram(BpmnModel bpmnModel) {
        return null;
    }

    @Override
    public InputStream generateJpgDiagram(BpmnModel bpmnModel, double v, boolean b) {
        return null;
    }

    @Override
    public BufferedImage generatePngImage(BpmnModel bpmnModel, double v) {
        return null;
    }
}
