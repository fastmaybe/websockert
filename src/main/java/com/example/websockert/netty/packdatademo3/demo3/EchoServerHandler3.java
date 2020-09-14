package com.example.websockert.netty.packdatademo3.demo3;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: liulang
 * @Date: 2020/9/14 15:57
 */
public class EchoServerHandler3 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
            ctx.channel().writeAndFlush("你好，欢迎关注我的微信公众号");
//            ctx.channel().writeAndFlush("你好");
        }
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("receive msg: "+msg.getClass());
        System.out.println("receive msg: "+msg);
    }
}
