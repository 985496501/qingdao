package com.self.boot.concurrent;

import lombok.*;
import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;


public class UnsafeTest {

    @Test
    public void compareAndSwapIntTest() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe)theUnsafe.get(null);
        SyncAid aid = new SyncAid(2);
        boolean state = unsafe.compareAndSwapInt(aid, unsafe.objectFieldOffset(SyncAid.class.getDeclaredField("state")),
                2, 3);

        System.out.println(state);
        System.out.println(aid.getState());
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class SyncAid {
        private volatile int state;
    }
}
