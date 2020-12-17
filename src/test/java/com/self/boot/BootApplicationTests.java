package com.self.boot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.self.boot.custom.MyCustomFunc;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

@Slf4j
@SpringBootTest
class BootApplicationTests {
    @Autowired
    private MyCustomFunc myCustomFunc;

    @Test
    public void annotationImportTest() {
        myCustomFunc.greet();
    }


    @Test
    public void countDownTest() {
        CountDownLatch l = new CountDownLatch(9);

    }


    @Test
    void objectMapperTest() throws JsonProcessingException {
        // obj -> json
        ObjectMapper objectMapper = new ObjectMapper();
        // 设置对象属性序列化 模式都参与序列化 但是一些null完全可以过滤掉 不返回
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        User user = new User("root", "A123.", "17862328960", "", null, LocalDate.now(), new Date(), null); //
        String s = objectMapper.writeValueAsString(user);
        log.info("{}", s);

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleUser simpleUser = objectMapper.readValue(s, SimpleUser.class);
        log.info("{}", simpleUser.toString());
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class User {
        private String userName;
        private String password;
        private String phoneNumber;
        private String email;
        private String realName;
        private LocalDate birthday;
        private Date createdAt;
        private SimpleUser simpleUser;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class SimpleUser {
        private String userName;
        private String password;
    }

}
