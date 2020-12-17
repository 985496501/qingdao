package com.self.boot.jvm;

import org.junit.jupiter.api.Test;

public class JcTest {

    @Test
    public void gcTest() {
        for (int i = 0; i < 10000000; i++) {
            Obj obj = new Obj();
        }
    }



    //
    static class Obj {
        private int i;

        public Obj() { }
    }
}
