package com.example.websockert.netty.packdatademo1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

/**
 * @Author: liulang
 * @Date: 2020/9/11 15:06
 */

/**
 * EchoServerHandler的作用主要是打印接收到的消息，然后发送响应给客户端：
 */
public class EchoServerHandler extends SimpleChannelInboundHandler<String> {



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("server receives message: " + msg.getBytes().length);
        System.out.println("server receives message: " + msg.trim());
        System.out.println();
//        for (int i = 0; i < 10; i++) {
////            ByteBuf buffer = getByteBuf(ctx);
////            ctx.channel().writeAndFlush(buffer);
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
////            ctx.channel().writeAndFlush("");
//        }
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);

        return buffer;
    }
}
