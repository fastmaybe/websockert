package com.example.websockert.netty.packdatademo3.demo1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author: liulang
 * @Date: 2020/9/14 11:31
 */
public class EchoServerHandler  extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
//            ctx.writeAndFlush("你好，欢迎关注我的微信公众号");
            ctx.channel().writeAndFlush("你好，欢迎关注我的微信公众号");
//            ctx.channel().writeAndFlush("你好");
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("recevie msg: "+s);
    }
}
