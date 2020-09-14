package com.example.websockert.netty.packdatademo3.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: liulang
 * @Date: 2020/9/14 14:07
 */
public class EchoServerHandler2 extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 2; i++) {
            ctx.writeAndFlush("你好");
//            ctx.channel().writeAndFlush("你好");
//            ctx.channel().writeAndFlush("你好");
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.getClass());
    }
}
