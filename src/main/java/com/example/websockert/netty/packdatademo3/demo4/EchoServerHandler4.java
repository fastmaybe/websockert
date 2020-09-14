package com.example.websockert.netty.packdatademo3.demo4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:01
 */
public class EchoServerHandler4 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 5; i++) {
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
            ctx.channel().writeAndFlush(Unpooled.wrappedBuffer("hello welcome ".getBytes("utf-8")));
//            ctx.channel().writeAndFlush("你好");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf){

            ByteBuf buf = (ByteBuf) msg;
            short version = buf.readShort();
            short len = buf.readShort();
            byte[] contentBytes = new byte[len];
            buf.readBytes(contentBytes);
            System.out.println(version);
            System.out.println(len);
            System.out.println(new String(contentBytes));
        }
    }
}
