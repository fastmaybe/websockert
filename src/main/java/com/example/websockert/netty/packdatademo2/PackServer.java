package com.example.websockert.netty.packdatademo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

/**
 * @Author: liulang
 * @Date: 2020/9/14 10:11
 */
public class PackServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        String delimiter = "_$";
        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            // 将delimiter设置到DelimiterBasedFrameDecoder中，经过该解码一器进行处理之后，源数据将会
                            // 被按照_$进行分隔，这里1024指的是分隔的最大长度，即当读取到1024个字节的数据之后，若还是未
                            // 读取到分隔符，则舍弃当前数据段，因为其很有可能是由于码流紊乱造成的
                            pipeline.addLast(new DelimiterBasedFrameDecoder(20, Unpooled.wrappedBuffer(delimiter.getBytes())));
                            // 将分隔之后的字节数据转换为字符串数据
                            pipeline.addLast(new StringDecoder());
                            // 这是我们自定义的一个编码器，主要作用是在返回的响应数据最后添加分隔符
                            pipeline.addLast(new DelimiterBasedFrameEncoder(delimiter));
                            // 最终处理数据并且返回响应的handler
                            pipeline.addLast(new EchoServerHandler());

                        }
                    });

            ChannelFuture cf = b.bind(7770).sync();
            cf.channel().closeFuture().sync();


        } finally {

            workGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
