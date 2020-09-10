package com.example.websockert.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author: liulang
 * @Date: 2020/9/10 17:32
 */
public class MyServer {

    public static void main(String[] args) throws InterruptedException {

        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup work = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap()
                    .group(boss,work)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .handler(new LoggingHandler(LogLevel.ERROR))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            //因为基于http协议 使用http的编解码器
                            pipeline.addLast(new HttpServerCodec());

                            //是以块方式填写
                            pipeline.addLast(new ChunkedWriteHandler());

                            /*
                             *说明 http在传输过程中 分段的  HttpObjectAggregator可以将多个段聚合
                             * 这也是为什么  当浏览器发送大量数据时候 会发送多次http请求
                             */
                            pipeline.addLast(new HttpObjectAggregator(8192));

                            /*
                             *说明：1 对应websocket 它的数据是以 帧（frame）形式传递
                             *     2 可以看到webSocketFrame 下面六个子类
                             *      3 浏览器请求时 ws://localhost:7000/hello 表示请求的uri
                             *      4 WebSocketServerProtocolHandler 核心功能就是将 http 协议升级为 ws 协议 保持长连接
                             *      5 是通过一个状态码
                             */
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello2"));

                            pipeline.addLast(new MyTextWebSocketFrameHanler());

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
