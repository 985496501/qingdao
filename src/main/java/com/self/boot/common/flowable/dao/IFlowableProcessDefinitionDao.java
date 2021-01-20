package com.self.boot.common.flowable.dao;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.vo.ProcessDefinitionQueryVo;
import com.self.boot.common.flowable.vo.ProcessDefinitionVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * process definition dao
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Mapper
public interface IFlowableProcessDefinitionDao {

    /**
     * 通过条件查询流程定义列表
     *
     * @param params 参数
     * @return ProcessDefinitionVo
     */
    Page<ProcessDefinitionVo> getPagerModel(ProcessDefinitionQueryVo params);

    /**
     * 通过流程定义id获取流程定义的信息
     *
     * @param procId 流程定义id
     * @return ProcessDefinitionVo
     */
    ProcessDefinitionVo getById(String procId);
}
