package com.example.websockert.netty.packdatademo3.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @Author: liulang
 * @Date: 2020/9/14 14:07
 */
public class EchoClientHandler2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.err.println(msg.getClass());
        ByteBuf buf = (ByteBuf) msg;

        short i = buf.readShort();
        System.out.println(i);
        System.out.println();
        System.out.println("===================");


    }
}
