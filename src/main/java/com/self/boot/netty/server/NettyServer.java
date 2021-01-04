package com.self.boot.netty.server;

import com.self.boot.netty.handler.StringCustomer;
import com.self.boot.netty.factory.NettyEventLoopFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * netty server
 *
 * 具体的api 后面具体学习一下 这个就跟着闪电侠学netty 然后明白网络通信原理
 * 可以使用netty手写一个http server, websocket server (主要就是这个)
 * netty的细节之处 和 笔记 听课解决然后源码解析等
 *
 * todo: netty是如何修复了jdk nio空轮询的bug
 * todo: 如何定义的环形链表 对 io操作进行连接的
 * todo: mqtt协议 netty怎么实现的
 *
 * 关键点： 系统调用 os kernel provides. 凡是系统调用都采用了异步编程思想 所有的系统调用io event都是异步的。
 *         event-driven async  但是io的读写时同步的。
 *         io
 *         zero-copy
 *         thread management
 *
 * 远程主机强迫关闭了一个现有的连接
 * todo: 如果远程主机突然强迫关闭连接 没有机会经历 tcp的4次握手断联 这个怎么处理这个IOException?
 * todo:  that reached at the tail of the pipeline. Please check your pipeline configuration. ???
 *
 * 参考下 Dubbo 是怎么写的这块
 *
 *
 * spring集成netty
 */
@Slf4j
@Component
public class NettyServer implements CommandLineRunner {
    /**
     * 默认的 io thread 数 核心数加1 与 32 的最小值
     */
    private static final int DEFAULT_IO_THREADS = Math.min(Runtime.getRuntime().availableProcessors() + 1, 1 << 5);

    /**
     * tcp服务器的默认端口
     */
    private static final int DEFAULT_PORT = 996;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;
    private final ServerBootstrap bootstrap;

    public NettyServer() {
        // default 1
        this.bossGroup = NettyEventLoopFactory.eventLoopGroup(1, "nettyBossGroup");
        this.workerGroup = NettyEventLoopFactory.eventLoopGroup(DEFAULT_IO_THREADS, "nettyWorkerGroup");
        this.bootstrap = new ServerBootstrap();
    }

    /**
     * 这个方法也不太行
     */
    public void open() {
        try {
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NettyEventLoopFactory.serverSocketChannel())
                    // 配置服务端socketChannel的一些参数   默认30s服务端就会发起4次挥手 断联
                    .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                    // 小报文是否延迟发送
//                .option(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 在这里就可以 io 操作 对报文就行自己的封装了
                            // netty提供了好多写好的  channelHandler (client)
                            socketChannel.pipeline()
                                    .addLast(new StringDecoder())
                                    .addLast(new StringDecoder())
                            .addLast(new StringCustomer());
                        }
                    });
            ChannelFuture channelFuture = bootstrap.bind(DEFAULT_PORT).addListener(new MyListener()).syncUninterruptibly();
            channelFuture.channel().closeFuture().syncUninterruptibly();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void run(String... args) {
        log.debug("spring初始化完毕 开启nettyServer");
        //open();
    }

    static class MyListener implements GenericFutureListener<ChannelFuture> {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            // todo:
            //
        }
    }

    public static void main(String[] args) {
        NettyServer nettyServer = new NettyServer();
        nettyServer.open();
    }
}
