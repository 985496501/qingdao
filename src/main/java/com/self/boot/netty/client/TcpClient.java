package com.self.boot.netty.client;

import com.self.boot.netty.factory.NettyEventLoopFactory;
import com.self.boot.netty.handler.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;

import java.io.IOException;

public class TcpClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        EventLoopGroup clientLoop = NettyEventLoopFactory.eventLoopGroup(1, "nettyClientLoop");
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(clientLoop)
                .channel(NettyEventLoopFactory.socketChannel())
                .option(ChannelOption.SO_TIMEOUT, 2)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                })
        .connect("localhost", 996).syncUninterruptibly();

    }
}
