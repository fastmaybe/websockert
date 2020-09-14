package com.example.websockert.netty.packdatademo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

/**
 * @Author: liulang
 * @Date: 2020/9/11 15:08
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("client receives message: " + msg.trim());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
//            ByteBuf buffer = getByteBuf(ctx);
//            ctx.channel().writeAndFlush(buffer);
            ctx.writeAndFlush("Hi, Welcome to Netty World!");
//            ctx.writeAndFlush(Unpooled.copiedBuffer("Hi, Welcome to Netty World!".getBytes()));
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
//            ctx.channel().writeAndFlush("");
        }

//        ctx.writeAndFlush("hello server!");
    }
    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "你好，欢迎关注我的微信公众号".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes(bytes);

        return buffer;
    }
}
