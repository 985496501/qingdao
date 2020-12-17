package com.self.boot.common.http;

public interface HttpService {
    /**
     * 发送post请求 请求方式 json bean for bean
     *
     * @param url           请求地址
     * @param body          请求对象封装 json 相应key的 java bean
     * @param responseClass 返回值的clazz
     * @param <T>           返回值泛型
     * @return clazzBean
     */
    <T> T postByJson(String url, Object body, Class<T> responseClass);
}
