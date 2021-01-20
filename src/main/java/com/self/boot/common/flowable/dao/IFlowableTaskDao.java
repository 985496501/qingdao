package com.self.boot.common.flowable.dao;


import com.github.pagehelper.Page;
import com.self.boot.common.flowable.vo.TaskQueryVo;
import com.self.boot.common.flowable.vo.TaskVo;
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
public interface IFlowableTaskDao {
    /**
     * 查询待办任务
     *
     * @param params 参数
     * @return
     */
    Page<TaskVo> getApplyingTasks(TaskQueryVo params);

    /**
     * 查询已办任务列表
     *
     * @param params 参数
     * @return
     */
    Page<TaskVo> getApplyedTasks(TaskQueryVo params);
}
