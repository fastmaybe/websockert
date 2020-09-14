package com.example.websockert.netty.packdatademo3.demo4;

import com.example.websockert.netty.packdatademo3.demo3.DYLengthFieldPrepender;
import com.example.websockert.netty.packdatademo3.demo3.EchoClientHandler3;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:01
 */
public class PackClient4 {

    public static void main(String[] args) throws UnsupportedEncodingException {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 这里将LengthFieldBasedFrameDecoder添加到pipeline的首位，因为其需要对接收到的数据
                        // 进行长度字段解码，这里也会对数据进行粘包和拆包处理
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));

                        //加入自定义 解码器
                        ch.pipeline().addLast(new PackageSpliter());
                        // LengthFieldPrepender是一个编码器，主要是在响应字节数据前面添加字节长度字段



                        //加入自定义 编码器
                        ch.pipeline().addLast(new DYEncoder());
                        // 处理客户端的请求的数据，并且进行响应
                        ch.pipeline().addLast(new EchoClientHandler4());
                    }
                });

        ChannelFuture cf = b.connect("127.0.0.1", 7770);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){
            String msg = scanner.nextLine();

            for (int i = 0; i < 3; i++) {
                cf.channel().writeAndFlush(Unpooled.wrappedBuffer(msg.getBytes("utf-8")));
            }
        }


    }
}
