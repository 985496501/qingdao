package com.self.boot.common.flowable.dao;


import com.self.boot.common.flowable.vo.CommentVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * comment
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Mapper
public interface IFlowableCommentDao {

    /**
     * 通过流程实例id获取审批意见列表
     *
     * @param procId 流程id
     * @return 评论列表
     */
    List<CommentVo> getFlowCommentVosByProcessInstanceId(String procId);
}
