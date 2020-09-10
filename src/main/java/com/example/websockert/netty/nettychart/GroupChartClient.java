package com.example.websockert.netty.nettychart;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

/**
 * @Author: liulang
 * @Date: 2020/9/10 16:30
 */
public class GroupChartClient {

    private String host;

    private int port;

    public GroupChartClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() throws InterruptedException {

        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();
                            //加入 编码解码器
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

                            //加入业务处handler
                            pipeline.addLast(new GroupChartClientHandler());
                        }
                    });

            ChannelFuture cf = b.connect(host, port).sync();

           //获取channel
            Channel channel = cf.channel();

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNextLine()){
                String msg = scanner.nextLine();
                channel.writeAndFlush(msg+"\r\n");
            }


        } finally {

            group.shutdownGracefully();
        }


    }

    public static void main(String[] args) throws InterruptedException {

        GroupChartClient client = new GroupChartClient("127.0.0.1", 6668);
        client.run();
    }
}
