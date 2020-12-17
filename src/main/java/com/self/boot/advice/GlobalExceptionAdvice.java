package com.self.boot.advice;

import com.self.boot.common.result.R;
import com.self.boot.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

/**
 * Spring 内置了 HttpStatus
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionAdvice<T> {
    @InitBinder
    public void initBinder(WebDataBinder binder) { }

    @ResponseStatus(HttpStatus.OK)
   // @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R ok(T data) {
        //log.error("=============>> exception information: \n method: {}, supportedHttpMethods: {}", e.getMethod(), e.getSupportedHttpMethods());
        return new R(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(), data);
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object httpRequestMethodNotSupportedExceptionAdvice(HttpRequestMethodNotSupportedException e) {
        log.error("=============>> exception information: \n method: {}, supportedHttpMethods: {}", e.getMethod(), e.getSupportedHttpMethods());
        return new R(HttpStatus.OK.value(), HttpStatus.OK.getReasonPhrase(),  e.getSupportedHttpMethods());
    }

    //@ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public Object baseExceptionAdvice(BaseException e) {
        return new R(e.getCode(), e.getMessage(), null);
    }
}
