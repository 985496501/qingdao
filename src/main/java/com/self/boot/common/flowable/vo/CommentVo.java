package com.self.boot.common.flowable.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 评论视图
 * 针对于流程实例
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentVo implements Serializable {
    /**
     * 任务id
     */
    protected String taskId;
    /**
     * 添加人
     */
    protected String userId;
    /**
     * 用户的名称
     */
    protected String userName;
    /**
     * 用户的头像链接
     */
    protected String userUrl;
    /**
     * 流程实例id
     */
    protected String processInstanceId;
    /**
     * 意见信息
     */
    protected String message;
    /**
     * 时间
     */
    protected Date time;
    /**
     * 审批类型
     */
    private String type;
    /**
     * 类型名称
     */
    private String typeName;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 评论全信息
     */
    private String fullMsg;
}
