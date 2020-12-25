package com.self.boot.conf;

import com.self.boot.common.result.WrapperReturnValueHandler;
import com.self.boot.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 这就属于定制
 * refer to {@code WebMvcConfigurationSupport}
 */
@EnableWebMvc
@Configuration
public class MvcAutoConfiguration implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/sign");
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 可以在这里使用 fastJson 来替换 spring mvc 自带的 jackson.
        // todo: replace jackson with fastJson
        converters.add(mappingJackson2HttpMessageConverter);
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        handlers.add(new WrapperReturnValueHandler());
    }
}
