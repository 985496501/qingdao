package com.self.boot.common.flowable.service;

import com.self.boot.common.flowable.vo.ModelVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程模型导入顶级接口
 *
 * @author jinyun
 */
public interface IFlowableModelService {

    /**
     * 导入模型
     *
     * @param file 文件
     * @return
     */
    String importProcessModel(MultipartFile file);

    /**
     * 添加模型
     *
     * @param modelVo 流程模型对象
     * @return
     */
    String addModel(ModelVo modelVo);
}
