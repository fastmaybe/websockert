package com.example.websockert.netty.packdatademo2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: liulang
 * @Date: 2020/9/14 10:39
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("client receives message: " + msg.trim());
    }
}
