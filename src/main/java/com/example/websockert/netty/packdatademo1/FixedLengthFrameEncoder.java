package com.example.websockert.netty.packdatademo1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author: liulang
 * @Date: 2020/9/11 15:04
 */

/**
 *   这里FixedLengthFrameEncoder实现了decode()方法，在该方法中，主要是将消息长度不足20的消息进行空格补全。
 */
public class FixedLengthFrameEncoder extends MessageToByteEncoder<String> {
    private int length;

    public FixedLengthFrameEncoder(int length) {
        this.length = length;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out) throws Exception {
        // 对于超过指定长度的消息，这里直接抛出异常 或者不处理
        if (msg.getBytes().length>length){
            System.err.println("message length is too large, it's limited "+length);
            throw new UnsupportedOperationException(
                    "message length is too large, it's limited " + length);
        }
//        if (msg.length() > length) {
//            throw new UnsupportedOperationException(
//                    "message length is too large, it's limited " + length);
//        }

        //长度不足  空格补全
//        if (msg.length() < length) {
        if (msg.getBytes().length < length) {
            msg = addSpace(msg);
        }
        System.out.println(msg.getBytes().length);

        ctx.writeAndFlush(Unpooled.wrappedBuffer(msg.getBytes()));
    }

    // 进行空格补全
    private String addSpace(String msg) {
        StringBuilder builder = new StringBuilder(msg);
        for (int i = 0; i < length - msg.getBytes().length; i++) {
            builder.append(" ");
        }

        return builder.toString();
    }

}
