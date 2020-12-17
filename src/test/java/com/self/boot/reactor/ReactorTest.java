package com.self.boot.reactor;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 响应式编程
 */
public class ReactorTest {
    @Test
    public void mapTest() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }


    }

    public Integer add(Integer integer) {
        return integer++;
    }


}
