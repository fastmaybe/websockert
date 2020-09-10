package com.example.websockert.netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author: liulang
 * @Date: 2020/9/8 18:12
 */
public class NettyHttpServer {

    public static void main(String[] args) {
        System.out.println(1 << 30);
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

//        try {
//            ServerBootstrap b = new ServerBootstrap()
//                    .group(boss,work)
//                    .channel(NioServerSocketChannel.class)
////                    .option(ChannelOption.SO_BACKLOG,128)
////                    .childOption(ChannelOption.SO_KEEPALIVE,true)
//                    .childHandler(new HttpChannelInitializer());
//
//            ChannelFuture cf = b.bind(7779).sync();
//
//            cf.channel().closeFuture().sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            boss.shutdownGracefully();
//            work.shutdownGracefully();
//        }


    }
}
