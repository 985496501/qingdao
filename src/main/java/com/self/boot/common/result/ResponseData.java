package com.self.boot.common.result;


import com.self.boot.common.exception.BaseException;
import org.springframework.http.HttpStatus;

/**
 * 先new 这个吧
 * @param <T>
 */
public class ResponseData<T> {
    private final int code;
    private final String msg;
    private final T data;

    private ResponseData(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseData<T> success(T data) {
        return new ResponseData<>(HttpStatus.OK.value(), HttpStatus.OK.toString(), data);
    }

    public static <T> ResponseData<T> success() {
        return success(null);
    }

    public static ResponseData<Throwable> error(BaseException e) {
        return new ResponseData<>(e.getCode(), e.getMessage(), e.getCause());
    }

    public static ResponseData<Throwable> error(Exception e) {
        return new ResponseData<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), e.getCause());
    }
}
