package com.example.websockert.web.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.example.websockert.web.websocket.WebSocketChildChannelHandler;

/**
 * @Author: liulang
 * @Date: 2020/8/26 10:08
 * Netty WebSocket服务器 使用独立线程开启
 */
@Component
public class WebSocketServer implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    private int port =3333;
    private ChannelFuture serverChannelFuture;
    @Autowired
    private WebSocketChildChannelHandler childChannelHandler;

    @Override
    public void run() {
        build();
    }

    @Bean
    public EventLoopGroup bossGroup(){
        //处理客户端链接请求
       return new NioEventLoopGroup(1);
    }

    @Bean
    public EventLoopGroup workerGroup(){
        //处理客户端I/O操作
        return new NioEventLoopGroup();
    }

    @Bean
    public ServerBootstrap bootstrap(){
        //netty启动引导类
        return  new ServerBootstrap();
    }

    @Autowired
    private EventLoopGroup bossGroup;
    @Autowired
    private EventLoopGroup workerGroup;

    @Autowired
    private ServerBootstrap bootstrap;
    /**
     * 启动netty
     */
    private void build() {

        try {
            long begin = System.currentTimeMillis();

             bootstrap
                    //boss辅助客户端的tcp连接请求  worker负责与客户端之前的读写操作
                    .group(bossGroup, workerGroup)
                    //配置客户端的channel类型
                    .channel(NioServerSocketChannel.class)
                    //配置TCP参数 握手字符长度设置
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //TCP_NODELAY算法，尽可能发送大块数据，减少充斥的小块数据
                    .option(ChannelOption.TCP_NODELAY, true)
                    //开启心跳包活机制，就是客户端、服务端建立连接处于ESTABLISHED状态，超过2小时没有交流，机制会被启动
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    //配置固定长度接收缓存区分配器
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(1))
                    .childHandler(childChannelHandler);

            long end = System.currentTimeMillis();

            logger.info("Netty Websocket服务器启动完成，耗时 " + (end - begin) + " ms，已绑定端口 " + port + " 阻塞式等候客户端连接");

            serverChannelFuture = bootstrap.bind(port).sync();
        } catch (Exception e) {
            logger.info(e.getMessage());
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            e.printStackTrace();
        }

    }

    /**
     * 描述：关闭Netty Websocket服务器，主要是释放连接
     *     连接包括：服务器连接serverChannel，
     *     客户端TCP处理连接bossGroup，
     *     客户端I/O操作连接workerGroup
     *
     *     若只使用
     *         bossGroupFuture = bossGroup.shutdownGracefully();
     *         workerGroupFuture = workerGroup.shutdownGracefully();
     *     会造成内存泄漏。
     */
    public void close(){
        serverChannelFuture.channel().close();
        Future<?> bossGroupFuture = bossGroup.shutdownGracefully();
        Future<?> workerGroupFuture = workerGroup.shutdownGracefully();

        try {
            bossGroupFuture.await();
            workerGroupFuture.await();
        } catch (InterruptedException ignore) {
            ignore.printStackTrace();
        }
    }
}
