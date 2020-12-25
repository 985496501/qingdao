package com.self.boot.interceptor;


import com.self.boot.common.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {
    /**
     * 认证token
     */
    private static final String ACCESS_TOKEN = "token";

    private final RedisService redisService;

    /**
     * after HandlerMapping, before HandlerAdapter.
     *
     * @param request header
     * @param response then
     * @param handler handlerMethod
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(ACCESS_TOKEN);

        // handler HandlerMethod.  handler == controller
        log.info("====> \n {}", token);
        return true;
    }
}
