package com.example.websockert.netty.packdatademo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

/**
 * @Author: liulang
 * @Date: 2020/9/11 14:25
 */
public class PackClient {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            // 对服务端发送的消息进行粘包和拆包处理，由于服务端发送的消息已经进行了空格补全，
                            // 并且长度为20，因而这里指定的长度也为20
                            channel.pipeline().addLast(new FixedLengthFrameDecoder(Const.len));
                            // 将粘包和拆包处理得到的消息转换为字符串
                            channel.pipeline().addLast(new StringDecoder());
                            // 对客户端发送的消息进行空格补全，保证其长度为20
                            channel.pipeline().addLast(new FixedLengthFrameEncoder(Const.len));
                            // 客户端发送消息给服务端，并且处理服务端响应的消息
                            channel.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            ChannelFuture cf = b.connect("127.0.0.1", 7000).sync();

            //获取channel
            Channel channel = cf.channel();

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
//                channel.writeAndFlush(msg);
                channel.writeAndFlush(msg);
            }

//            cf.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
