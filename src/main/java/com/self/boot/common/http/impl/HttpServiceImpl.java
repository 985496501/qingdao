package com.self.boot.common.http.impl;

import com.self.boot.common.http.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpServiceImpl implements HttpService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public <T> T postByJson(String url, Object body, Class<T> responseClass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, httpEntity, responseClass);
    }
}
