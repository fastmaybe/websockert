package com.example.websockert.netty.packdatademo1.pakcdemo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author: liulang
 * @Date: 2020/9/11 16:56
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    private int counter = 0;
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String )msg;
        System.out.println(
                "This is " + ++counter +" times receive client [" + body +"]"
        );

        ByteBuf resp = Unpooled.copiedBuffer(body.getBytes());
        ctx.writeAndFlush(resp);
    }

    /**
     * 异常处理
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //打印异常栈跟踪
        cause.printStackTrace();

        // 关闭该Channel
        ctx.close();
    }
}
