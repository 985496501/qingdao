package com.self.boot.common.flowable.dao;

import com.github.pagehelper.Page;
import com.self.boot.common.flowable.vo.ProcessInstanceQueryVo;
import com.self.boot.common.flowable.vo.ProcessInstanceVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * process inst dao
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Mapper
@Repository
public interface IFlowableProcessInstanceDao {

    /**
     * 通过条件查询流程实例VO对象列表
     *
     * @param params 参数
     * @return
     */
    Page<ProcessInstanceVo> getPagerModel(ProcessInstanceQueryVo params);
}
