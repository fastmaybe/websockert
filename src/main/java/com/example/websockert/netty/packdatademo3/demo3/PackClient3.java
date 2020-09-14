package com.example.websockert.netty.packdatademo3.demo3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author: liulang
 * @Date: 2020/9/14 15:57
 */
public class PackClient3 {


    public static void main(String[] args) {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 这里将LengthFieldBasedFrameDecoder添加到pipeline的首位，因为其需要对接收到的数据
                        // 进行长度字段解码，这里也会对数据进行粘包和拆包处理
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));
                        // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段
                        ch.pipeline().addLast(new DYLengthFieldPrepender(2));

                        // 将分隔之后的字节数据转换为字符串数据
                        //加入解码器
                        ch.pipeline().addLast(new StringDecoder());
                        //加入编码器
                        ch.pipeline().addLast(new StringEncoder());


                        // 处理客户端的请求的数据，并且进行响应
                        ch.pipeline().addLast(new EchoClientHandler3());
                    }
                });

        ChannelFuture cf = b.connect("127.0.0.1", 7770);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();

            for (int i = 0; i < 3; i++) {
                cf.channel().writeAndFlush(msg);
            }
        }


    }
}
