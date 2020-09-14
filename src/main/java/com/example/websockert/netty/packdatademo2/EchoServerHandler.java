package com.example.websockert.netty.packdatademo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;

/**
 * @Author: liulang
 * @Date: 2020/9/14 10:34
 */
public class EchoServerHandler  extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println("receives message: " + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                for (int i = 0; i < 10; i++) {
//            ByteBuf buffer = getByteBuf(ctx);
//            ctx.channel().writeAndFlush(buffer);
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
//            ctx.channel().writeAndFlush("你好，欢迎关注我的微信公众号");
            ctx.channel().writeAndFlush("你好");
        }
    }

}
