package com.example.websockert.netty.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Author: liulang
 * @Date: 2020/9/8 13:43
 */
public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();

        try {


        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {

                        channel.pipeline().addLast(new NettyClientHandle());

                    }
                });

        ChannelFuture cf = bootstrap.connect("127.0.0.1", 6668).sync();

        //关闭通道监听
        cf.channel().closeFuture().sync();
        }
        finally {
            group.shutdownGracefully();
        }

    }
}
