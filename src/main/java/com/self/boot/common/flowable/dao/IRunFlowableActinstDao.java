package com.self.boot.common.flowable.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运行是的节点
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Mapper
@Repository
public interface IRunFlowableActinstDao {

    /**
     * 删除节点信息
     *
     * @param ids ids
     */
    void deleteRunActinstsByIds(List<String> ids);
}
