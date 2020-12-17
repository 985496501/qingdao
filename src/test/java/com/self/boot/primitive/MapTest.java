package com.self.boot.primitive;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class MapTest {
    @Test
    public void hashMapTest() {
        HashMap<String, String> map = new HashMap<>(16);
        String orDefault = map.getOrDefault("name", "liu");
        System.out.println(orDefault);

        String s = map.putIfAbsent("name", "apple");
        System.out.println(s);

        String age = map.put("age", "12");
        System.out.println(age);
    }
}
