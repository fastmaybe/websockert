package com.example.websockert.netty.demo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Author: liulang
 * @Date: 2020/9/8 11:44
 *
 * 1 自定义handle 集成规范 其中一个 比如ChannelInboundHandlerAdapter
 */
public class NettyServerHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel channel = ctx.channel();
        //将msg转未bytebuf
        ByteBuf buf = (ByteBuf) msg;

        System.out.println("客户端发送消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址:"+channel.remoteAddress());

        ctx.writeAndFlush(Unpooled.copiedBuffer("收到了...",CharsetUtil.UTF_8));

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public static void main(String[] args) {
        String property = System.getProperty("io.netty.eventLoopThreads");
        int i = Runtime.getRuntime().availableProcessors();
        System.out.println(property);
        System.out.println(i);
    }
}
