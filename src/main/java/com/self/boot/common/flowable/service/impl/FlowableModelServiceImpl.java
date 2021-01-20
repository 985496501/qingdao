package com.self.boot.common.flowable.service.impl;

import com.self.boot.common.flowable.service.IFlowableModelService;
import com.self.boot.common.flowable.vo.ModelVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 流程模型的实现
 * todo: 等待后面实现 流程模型的导入
 *
 *
 * @author jinyun
 */
@Service
@RequiredArgsConstructor
public class FlowableModelServiceImpl implements IFlowableModelService {

    @Override
    public String importProcessModel(MultipartFile file) {
        return null;
    }

    @Override
    public String addModel(ModelVo modelVo) {
        return null;
    }
}
