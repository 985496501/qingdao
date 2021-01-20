package com.self.boot.common.flowable.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * history act inst
 *
 * @author: jinyun
 * @date: 2021/1/20
 */
@Mapper
@Repository
public interface IHisFlowableActinstDao {

    /**
     * 删除节点信息
     *
     * @param ids ids
     */
    void deleteHisActinstsByIds(List<String> ids);
}
