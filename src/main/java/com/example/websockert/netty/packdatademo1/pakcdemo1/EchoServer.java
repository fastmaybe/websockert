package com.example.websockert.netty.packdatademo1.pakcdemo1;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author: liulang
 * @Date: 2020/9/11 16:55
 */
public class EchoServer {

    private final static int port = 8080;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        // 创建EventLoopGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        // 创建EventLoopGroup
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(
                                new FixedLengthFrameDecoder(20));
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(serverHandler);
                    }
                });

        try {
            // 异步地绑定服务器;调用sync方法阻塞等待直到绑定完成
            ChannelFuture f = b.bind(port).sync();
            // 获取Channel的CloseFuture，并且阻塞当前线程直到它完成
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 优雅的关闭EventLoopGroup，释放所有的资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
