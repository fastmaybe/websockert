package com.example.websockert.netty.packdatademo3.demo1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: liulang
 * @Date: 2020/9/14 11:26
 */
public class PackServer {

    public static void main(String[] args) throws InterruptedException {

       EventLoopGroup boss = new NioEventLoopGroup(1);
       EventLoopGroup work = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap()
                .group(boss,work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 这里将LengthFieldBasedFrameDecoder添加到pipeline的首位，因为其需要对接收到的数据
                        // 进行长度字段解码，这里也会对数据进行粘包和拆包处理
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                        // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
                        ch.pipeline().addLast(new LengthFieldPrepender(2));

                        // 将分隔之后的字节数据转换为字符串数据
                        //加入编码器
                        ch.pipeline().addLast(new StringEncoder());
                        //加入解码器
                        ch.pipeline().addLast(new StringDecoder());

                        // 对经过粘包和拆包处理之后的数据进行json反序列化，从而得到User对象
//                        ch.pipeline().addLast(new JsonDecoder());
                        // 对响应数据进行编码，主要是将User对象序列化为json
//                        ch.pipeline().addLast(new JsonEncoder());
                        // 处理客户端的请求的数据，并且进行响应
                        ch.pipeline().addLast(new EchoServerHandler());
                    }
                });

        ChannelFuture cf = b.bind(7770).sync();



    }
}
