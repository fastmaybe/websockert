package com.example.websockert.netty.demo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: liulang
 * @Date: 2020/9/8 16:31
 */
public class ServerStringHandle extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        System.out.println("收到消息2："+s);
        ctx.writeAndFlush("你好客户端...2...");
    }
}
