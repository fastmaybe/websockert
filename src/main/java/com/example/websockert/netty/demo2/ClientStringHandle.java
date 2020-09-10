package com.example.websockert.netty.demo2;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author: liulang
 * @Date: 2020/9/8 16:30
 */
public class ClientStringHandle extends SimpleChannelInboundHandler<String> {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道已经就绪2...");
        ctx.writeAndFlush("hello 服务器... 2 ...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("收到消息2："+s);
    }
}
