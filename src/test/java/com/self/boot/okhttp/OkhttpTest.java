package com.self.boot.okhttp;

import com.self.boot.common.http.HttpService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Slf4j
@SpringBootTest
public class OkhttpTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private HttpService httpService;

    @Test
    public void getOrderStatusByNumberTest() {
        RequestData requestData = new RequestData(new OrderNumber("1299150006523138048"));
        Object o = httpService.postByJson("http://127.0.0.1:8182/api/undertake/order/getOrderStatusByNumber",
                requestData, Object.class);
        assert o != null;
    }

    @Data
    @AllArgsConstructor
    public static class RequestData {
        private Object data;
    }

    @Data
    @AllArgsConstructor
    public static class OrderNumber {
        private String orderNumber;
    }

    @Test
    public void jsonPostTest() {
        Object o = httpService.postByJson("", new Object(), Object.class);
        log.info("======={}", o.toString());
    }

    @Test
    public void formPostTest() {
        String url = "http://localhost:9876" + "/test/hate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>(2);
        multiValueMap.add("name", "刘津运");
        multiValueMap.add("age", 26);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(multiValueMap, headers);
        restTemplate.postForObject(url, httpEntity, Object.class, "sex");
    }


    @Test
    public void postAuthTest() {
        String url = "http://localhost:9876" + "/test/resource/authed?accessToken={accessToken}";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Object> httpEntity = new HttpEntity<>(new Object(), headers);
        restTemplate.postForObject(url, httpEntity, Object.class, "123456789465456");
    }


    @Test
    public void getPathTest() {
        String url = "http://localhost:9876/test/testPath/{name}/{age}";
        restTemplate.getForObject(url, Object.class, "liujinyun", 13213);
    }
}
