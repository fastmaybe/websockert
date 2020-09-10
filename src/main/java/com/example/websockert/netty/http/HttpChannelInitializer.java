package com.example.websockert.netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Author: liulang
 * @Date: 2020/9/8 18:19
 */
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        //获取管道
        ChannelPipeline pipeline = channel.pipeline();
        Channel channel1 = pipeline.channel();
        //加入netty提供的 http编码解码器 httpServerCodec 取名myHttpServerCodec
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());

        //增加自定义的 处理器
        pipeline.addLast(new MyHttpServerHandle());

    }
}
