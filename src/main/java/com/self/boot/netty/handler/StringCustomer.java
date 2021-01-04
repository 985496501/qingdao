package com.self.boot.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义的一个字符串消费者
 * <p>
 * 可能会提供一些需求 按照需求 定制开发自己的web服务器 可以自定义自己的报文格式
 * 比如 dubbo://username:password@host:port/resource_url
 */
@Slf4j
public class StringCustomer extends ChannelInboundHandlerAdapter {

    private static final AtomicInteger CONNECTION_COUNT = new AtomicInteger();

    /**
     * 一个连接上来就会回调这个方法
     * <p>
     * Kernel Accept_queue[socket]:   established tcp once add() 就会调用这个方法
     * 系统调用 select() 有新的连接上来了
     *
     * @param ctx
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) {
        //   "/127.0.0.1:49897"
        String s = ctx.channel().remoteAddress().toString();
        CONNECTION_COUNT.getAndIncrement();
        log.debug("一个连接成功: {}, 目前活跃连接数: {}", s, CONNECTION_COUNT);
        // todo: 这样是不是可以 查看有多少tcp正在连接呢????  这个netty也绝对可以实现
        // 有这个接口吗？ 或者 主动 系统调用返回 活跃的连接数 这个肯定有 但是在哪？ 看netty对 selector 的封装 select() 这个没有回调方法吗？
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        ctx.channel().writeAndFlush(msg);
        ctx.alloc().buffer().clear();
    }



    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        CONNECTION_COUNT.getAndDecrement();
        log.debug("一个客户端断开了连接 剩余活跃连接数： {}", CONNECTION_COUNT);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("发生了未知异常");
    }
}
