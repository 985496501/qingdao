package com.self.boot.netty.factory;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 提供 生成 event loop group 的工厂方法
 * 因为epoll目前只有linux
 */
public class NettyEventLoopFactory {

    /**
     * 获取eventLoopGroup
     *
     * @param threads 线程数
     * @param threadFactoryName 线程工厂命名 阿里开发规范要求必须 以有意义的名称
     *                          有利于 查看线程状态
     * @return eventLoopGroup
     */
    public static EventLoopGroup eventLoopGroup(int threads, String threadFactoryName) {
        ThreadFactory threadFactory = new DefaultThreadFactory(threadFactoryName, true);
        return supportEpoll() ? new EpollEventLoopGroup(threads, threadFactory) :
                new NioEventLoopGroup(threads, threadFactory);
    }

    /**
     * 快速获取 serverSocket
     *
     * @return serverSocket
     */
    public static Class<? extends ServerSocketChannel> serverSocketChannel() {
        return supportEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;
    }

    /**
     * 获取 client Socket
     *
     * @return socketChannel
     */
    public static Class<? extends SocketChannel> socketChannel() {
        return supportEpoll() ? EpollSocketChannel.class : NioSocketChannel.class;
    }

    /**
     * default implementation is so simple.
     *
     * @return enable epoll
     */
    private static boolean supportEpoll() {
        return Epoll.isAvailable();
    }
}
