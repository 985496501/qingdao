package com.self.boot.json;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试fastjson的使用方法 :
 * 1> jsonStr --> obj
 * 2> obj --> jsonStr
 */
@Slf4j
public class FastJsonTest {

    @Test
    public void toStrTest() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("name", "jinyun");
        map.put("age", 25);
        JSONObject jsonObject = new JSONObject(map);
        String jsonString = jsonObject.toJSONString();
        // to json string
        System.out.println(jsonString);

        // static to Bean
        A a1 = JSONObject.toJavaObject(jsonObject, A.class);
        log.debug(a1.toString());

        // map --> jsonObject --> bean
        A a = jsonObject.toJavaObject(A.class);
        System.out.println(a);

        JSONObject jsonObj = JSONObject.parseObject(jsonString);
        String name = jsonObj.getObject("name", String.class);
        System.out.println(name);
    }


    @Data
    static class A {
        private Integer age;
        private String name;
    }
}
