package com.self.boot.netty;

import io.netty.channel.epoll.Epoll;
import org.junit.jupiter.api.Test;

public class EpollTest {

    @Test
    public void epollAvailableTest() {
        // 默认windows是不支持的  apparently print false.
        // linux 才会true
        System.out.println(Epoll.isAvailable());
    }



}
