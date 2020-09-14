package com.example.websockert.netty.packdatademo1;

/**
 * @Author: liulang
 * @Date: 2020/9/11 13:41
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 测试沾包 拆包
 */
public class PackServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 这里将FixedLengthFrameDecoder添加到pipeline中，指定长度为128
                            channel.pipeline().addLast(new FixedLengthFrameDecoder(Const.len));
                            // 将前一步解码得到的数据转码为字符串
                            channel.pipeline().addLast(new StringDecoder());
                            // 这里FixedLengthFrameEncoder是我们自定义的，用于将长度不足128的消息进行补全空格
                            channel.pipeline().addLast(new FixedLengthFrameEncoder(Const.len));
                            // 最终的数据处理
                            channel.pipeline().addLast(new EchoServerHandler());

                        }
                    });

            ChannelFuture cf = b.bind(7000).sync();



            cf.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();

        }


    }
}
