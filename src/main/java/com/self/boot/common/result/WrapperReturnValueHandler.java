package com.self.boot.common.result;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 需要把这个自定义的handler加入到 {@code RequestMappingHandlerAdapter}
 * 不然自定义的不执行
 */
@Slf4j
public class WrapperReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        log.debug("===============================================");
        if (returnType.hasMethodAnnotation(NotWrapper.class)) {
            return false;
        }

        return true;
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        System.out.println(returnValue);
    }
}
