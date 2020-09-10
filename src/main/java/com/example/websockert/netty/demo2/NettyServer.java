package com.example.websockert.netty.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: liulang
 * @Date: 2020/9/8 16:06
 */
public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
       EventLoopGroup bossGroup = new NioEventLoopGroup(1);
       EventLoopGroup workGroup = new NioEventLoopGroup();

       try {
           ServerBootstrap b = new ServerBootstrap()
                   .group(bossGroup,workGroup)
                   //指定通道类型
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG,128)
                   .childOption(ChannelOption.SO_KEEPALIVE,true)
                   .childHandler(new ChannelInitializer<SocketChannel>() {

                       @Override
                       protected void initChannel(SocketChannel channel) throws Exception {
                           ChannelPipeline pipeline = channel.pipeline();
                           //加入相关handler
                           pipeline.addLast("decoder", new StringDecoder());
                           pipeline.addLast("encoder", new StringEncoder());
                           pipeline.addLast(new ServerStringHandle());
                       }
                   });

           ChannelFuture cf = b.bind(6668).sync();

           cf.channel().closeFuture().sync();
       }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
       }

    }
}
