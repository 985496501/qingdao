package com.self.boot.common.result;

import com.self.boot.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Spring 内置了 HttpStatus
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionAdvice<T>{

    @ExceptionHandler(BaseException.class)
    public Object baseExceptionAdvice(BaseException e) {
        return ResponseData.error(e);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object httpRequestMethodNotSupportedExceptionAdvice(HttpRequestMethodNotSupportedException e) {
        return ResponseData.error(e);
    }
}
