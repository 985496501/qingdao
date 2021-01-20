package com.self.boot.common.flowable.service;


import com.self.boot.common.flowable.vo.CommentVo;

import java.util.List;

/**
 * 审批意见的顶级接口
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
public interface IFlowableCommentService {

    /**
     * 添加备注
     *
     * @param comment 参数
     */
    void addComment(CommentVo comment);

    /**
     * 通过流程实例id获取审批意见列表
     *
     * @param processInstanceId 流程实例id
     * @return
     */
    List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId);
}
