package com.self.boot;

import com.self.boot.custom.MyCustomFunc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;


@Import(MyCustomFunc.class)
@SpringBootApplication //(exclude = RedisAutoConfiguration.class)
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootApplication.class, args);
    }

}
