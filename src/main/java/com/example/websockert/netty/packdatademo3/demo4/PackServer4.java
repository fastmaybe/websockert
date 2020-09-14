package com.example.websockert.netty.packdatademo3.demo4;

import com.example.websockert.netty.packdatademo3.demo3.DYLengthFieldPrepender;
import com.example.websockert.netty.packdatademo3.demo3.EchoServerHandler3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:01
 */
public class PackServer4 {

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
//                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 0, 2, 0, 2));

                        //加入自定义解码器
                        ch.pipeline().addLast(new PackageSpliter());

                        //加入自定义编码器
                        ch.pipeline().addLast(new DYEncoder());

                        ch.pipeline().addLast(new EchoServerHandler4());
                    }
                });

        ChannelFuture cf = b.bind(7770).sync();



    }
}
