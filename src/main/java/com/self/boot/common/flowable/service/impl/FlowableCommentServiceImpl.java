package com.self.boot.common.flowable.service.impl;


import com.self.boot.common.flowable.cmd.AddHisCommentCmd;
import com.self.boot.common.flowable.core.CommentEnum;
import com.self.boot.common.flowable.dao.IFlowableCommentDao;
import com.self.boot.common.flowable.service.BaseProcessService;
import com.self.boot.common.flowable.service.IFlowableCommentService;
import com.self.boot.common.flowable.vo.CommentVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 审核意见的默认实现
 *
 * @author jinyun
 */
@Service
@RequiredArgsConstructor
public class FlowableCommentServiceImpl extends BaseProcessService implements IFlowableCommentService {
    private final IFlowableCommentDao flowableCommentDao;

    @Override
    public void addComment(CommentVo comment) {
        managementService.executeCommand(new AddHisCommentCmd(comment.getTaskId(), comment.getUserId(), comment.getProcessInstanceId(),
                comment.getType(), comment.getMessage()));
    }

    @Override
    public List<CommentVo> getFlowCommentVosByProcessInstanceId(String processInstanceId) {
        List<CommentVo> datas = flowableCommentDao.getFlowCommentVosByProcessInstanceId(processInstanceId);
        datas.forEach(commentVo -> {
            commentVo.setTypeName(CommentEnum.getEnumMsgByType(commentVo.getType()));
        });
        return datas;
    }
}
