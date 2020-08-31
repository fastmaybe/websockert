package com.example.websockert.web.websocket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: liulang
 * @Date: 2020/8/26 11:01
 */
@Component
public class WebSocketChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Resource(name = "webSocketServerHandler")
    private ChannelHandler webSocketServerHandler;

    @Resource(name = "httpRequestHandler")
    private ChannelHandler httpRequestHandler;


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //HttpServerCodec：将请求和应答消息解码为HTTP消息
        ch.pipeline().addLast("http-codec",new HttpServerCodec());
        //  把HTTP头、HTTP体拼成完整的HTTP请求
        ch.pipeline().addLast("aggregator",new HttpObjectAggregator(65536));
        //ChunkedWriteHandler：向客户端发送HTML5文件 方便大文件传输，不过实质上都是短的文本数据
        ch.pipeline().addLast("http-chunked",new ChunkedWriteHandler());
        // 在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast("http-handler",httpRequestHandler);
        // 在管道中添加我们自己的接收数据实现方法
        ch.pipeline().addLast("websocket-handler",webSocketServerHandler);
    }
}
