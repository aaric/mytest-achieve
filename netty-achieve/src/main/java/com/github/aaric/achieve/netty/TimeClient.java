package com.github.aaric.achieve.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * TimeClient
 *
 * @author Aaric, created on 2017-06-20T10:20.
 * @since 1.0-SNAPSHOT
 */
public class TimeClient {

    public void connect(String inetHost, int inetPort) {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>(){

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new TimeClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect(inetHost, inetPort)
                    .addListener(new ChannelFutureListener(){

                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if(!future.isSuccess()) {
                                System.out.println("failure");
                                if(!workerGroup.isShutdown()) {
                                    System.out.println("shutdown");
                                    workerGroup.shutdownGracefully();
                                }
                            }
                        }
                    })
                    .sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        workerGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        new TimeClient().connect("localhost", 7211);
        System.out.println("over!");
    }
}
