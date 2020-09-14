package com.example.websockert.netty.packdatademo3.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: liulang
 * @Date: 2020/9/14 15:57
 */
public class EchoClientHandler3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg: "+msg.getClass());
        System.out.println("receive msg: "+msg);
    }
}
