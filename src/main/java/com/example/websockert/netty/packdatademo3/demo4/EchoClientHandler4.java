package com.example.websockert.netty.packdatademo3.demo4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * @Author: liulang
 * @Date: 2020/9/14 18:02
 */
public class EchoClientHandler4 extends ChannelInboundHandlerAdapter {

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
