package com.self.boot.common.flowable.conf;

import org.flowable.bpmn.model.AssociationDirection;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;
import org.flowable.image.impl.DefaultProcessDiagramGenerator;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;

/**
 * 自定义扩展画图工具 继承自框架默认的画图工具
 * 没有特殊定制的需求 直接使用default即可\
 * 如果自定义 Diagram Canvas 还需要自定义 Diagram Generator
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas {
    /**
     * highlight sequence flow color
     */
    protected static Color HIGHLIGHT_SEQUENCEFLOW_COLOR = Color.GREEN;

    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
    }

    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType) {
        super(width, height, minX, minY, imageType);
    }


    /**
     * 高亮节点设置
     */
    @Override
    public void drawHighLight(int x, int y, int width, int height) {
        new DefaultProcessDiagramGenerator();
        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();
        //设置高亮节点的颜色
        g.setPaint(HIGHLIGHT_COLOR);
        g.setStroke(THICK_TASK_BORDER_STROKE);

        RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
        g.draw(rect);

        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

    /**
     * 画线颜色设置
     */
    @Override
    public void drawConnection(int[] xPoints, int[] yPoints, boolean conditional, boolean isDefault, String connectionType,
                               AssociationDirection associationDirection, boolean highLighted, double scaleFactor) {

        Paint originalPaint = g.getPaint();
        Stroke originalStroke = g.getStroke();

        g.setPaint(CONNECTION_COLOR);
        if ("association".equals(connectionType)) {
            g.setStroke(ASSOCIATION_STROKE);
        } else if (highLighted) {
            //设置线的颜色
            g.setPaint(HIGHLIGHT_SEQUENCEFLOW_COLOR);
            g.setStroke(HIGHLIGHT_FLOW_STROKE);
        }

        for (int i = 1; i < xPoints.length; i++) {
            int sourceX = xPoints[i - 1];
            int sourceY = yPoints[i - 1];
            int targetX = xPoints[i];
            int targetY = yPoints[i];
            Line2D.Double line = new Line2D.Double(sourceX, sourceY, targetX, targetY);
            g.draw(line);
        }

        if (isDefault) {
            Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            drawDefaultSequenceFlowIndicator(line, scaleFactor);
        }

        if (conditional) {
            Line2D.Double line = new Line2D.Double(xPoints[0], yPoints[0], xPoints[1], yPoints[1]);
            drawConditionalSequenceFlowIndicator(line, scaleFactor);
        }

        if (associationDirection == AssociationDirection.ONE || associationDirection == AssociationDirection.BOTH) {
            Line2D.Double line = new Line2D.Double(xPoints[xPoints.length - 2], yPoints[xPoints.length - 2], xPoints[xPoints.length - 1], yPoints[xPoints.length - 1]);
            drawArrowHead(line, scaleFactor);
        }
        if (associationDirection == AssociationDirection.BOTH) {
            Line2D.Double line = new Line2D.Double(xPoints[1], yPoints[1], xPoints[0], yPoints[0]);
            drawArrowHead(line, scaleFactor);
        }
        g.setPaint(originalPaint);
        g.setStroke(originalStroke);
    }

}
