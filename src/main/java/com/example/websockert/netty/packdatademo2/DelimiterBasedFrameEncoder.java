package com.example.websockert.netty.packdatademo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author: liulang
 * @Date: 2020/9/14 10:34
 */
public class DelimiterBasedFrameEncoder extends MessageToByteEncoder<String> {
    private String delimiter;
    public DelimiterBasedFrameEncoder(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf byteBuf) throws Exception {
        // 在响应的数据后面添加分隔符
        ctx.writeAndFlush(Unpooled.wrappedBuffer((msg + delimiter).getBytes()));
    }
}
