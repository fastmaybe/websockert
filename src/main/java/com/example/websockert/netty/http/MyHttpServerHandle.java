package com.example.websockert.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author: liulang
 * @Date: 2020/9/8 18:25
 */
public class MyHttpServerHandle extends SimpleChannelInboundHandler<HttpObject> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //先判断是不是 httpRequest
        if (msg instanceof HttpRequest){

            System.out.println("msg 类型"+msg.getClass());

            HttpRequest request = (HttpRequest) msg;


            //回复给浏览器
            ByteBuf buf = Unpooled.copiedBuffer("hello 这是服务器", CharsetUtil.UTF_8);
            //构造一个一个http响应

            FullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

            res.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            res.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());

            ctx.writeAndFlush(res);

        }
    }
}
